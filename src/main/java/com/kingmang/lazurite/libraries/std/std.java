package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.runtime.Types.*;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.utils.ValueUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Supplier;

public class std implements Library {
    @Override
    public void init(){
        LzrMap std = new LzrMap(3);
        LzrMap integerFunctions = new LzrMap(8);
        LzrMap doubleFunctions = new LzrMap(5);
        LzrMap stringFunctions = new LzrMap(3);
        LzrMap arrayDeque = new LzrMap(4);
        std.set("flatmap", new flatmap());
        std.set("thread", new thread());

        integerFunctions.set("bitCount", IntegerClass::bitCount);
        integerFunctions.set("max", IntegerClass::max);
        integerFunctions.set("min", IntegerClass::min);
        integerFunctions.set("compare", IntegerClass::compare);
        integerFunctions.set("parseInt", IntegerClass::parseInt);
        integerFunctions.set("decode", IntegerClass::decode);
        integerFunctions.set("signum", IntegerClass::signum);
        integerFunctions.set("compareUnsigned", IntegerClass::compareUnsigned);
        integerFunctions.set("MAX_VALUE", new LzrNumber(Integer.MAX_VALUE));
        integerFunctions.set("MIN_VALUE", new LzrNumber(Integer.MIN_VALUE));

        doubleFunctions.set("max", DoubleClass::max);
        doubleFunctions.set("min", DoubleClass::min);
        doubleFunctions.set("doubleToLongBits", DoubleClass::doubleToLongBits);
        doubleFunctions.set("parseDouble", DoubleClass::parseDouble);
        doubleFunctions.set("compare", DoubleClass::compare);
        doubleFunctions.set("MAX_VALUE", new LzrNumber(Double.MAX_VALUE));
        doubleFunctions.set("MIN_VALUE", new LzrNumber(Double.MIN_VALUE));

        stringFunctions.set("valueOf", StringClass::valueOf);
        stringFunctions.set("format", StringClass::format);
        stringFunctions.set("join", StringClass::join);
        stringFunctions.set("CASE_INSENSITIVE_ORDER", new LzrString(String.CASE_INSENSITIVE_ORDER.toString()));

        arrayDeque.set("add", LzrArrayDeque::addToQueue);
        arrayDeque.set("remove", LzrArrayDeque::remove);
        arrayDeque.set("size", LzrArrayDeque::sizeQueue);
        arrayDeque.set("toArray", LzrArrayDeque::toArray);

        Variables.define("arrayDeque", arrayDeque);
        Variables.define("Double", doubleFunctions);
        Variables.define("String", stringFunctions);
        Variables.define("Integer", integerFunctions);
        Variables.define("std", std);


        Keyword.put("hashMap", mapFunction(HashMap::new));
        Keyword.put("linkedHashMap", mapFunction(LinkedHashMap::new));
        Keyword.put("concurrentHashMap", mapFunction(ConcurrentHashMap::new));
        Keyword.put("treeMap", sortedMapFunction(TreeMap::new, TreeMap::new));
        Keyword.put("concurrentSkipListMap", sortedMapFunction(ConcurrentSkipListMap::new, ConcurrentSkipListMap::new));

    }

    public final static class flatmap implements Function {

        @Override
        public LzrValue execute(LzrValue... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRException("TypeExeption ", "Array expected in first argument");
            }
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return flatMapArray((LzrArray) args[0], mapper);
        }

        private LzrValue flatMapArray(LzrArray array, Function mapper) {
            final List<LzrValue> values = new ArrayList<>();
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final LzrValue inner = mapper.execute(array.get(i));
                if (inner.type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ", "Array expected " + inner);
                }
                for (LzrValue value : (LzrArray) inner) {
                    values.add(value);
                }
            }
            return new LzrArray(values);
        }
    }
    public final class thread implements Function {

        @Override
        public LzrValue execute(LzrValue... args) {
            Arguments.checkAtLeast(1, args.length);

            Function body;
            if (args[0].type() == Types.FUNCTION) {
                body = ((LzrFunction) args[0]).getValue();
            } else {
                body = Keyword.get(args[0].asString());
            }

            final LzrValue[] params = new LzrValue[args.length - 1];
            if (params.length > 0) {
                System.arraycopy(args, 1, params, 0, params.length);
            }

            final Thread thread = new Thread(() -> body.execute(params));
            thread.setUncaughtExceptionHandler(Console::handleException);
            thread.start();
            return LzrNumber.ZERO;
        }
    }

    public static final class LzrArrayDeque {
        static Deque<LzrValue> queue = new ArrayDeque<>();

        public static LzrValue addToQueue(LzrValue[] args) {
            Arguments.check(1,  args.length);
            queue.add(args[0]);
            return LzrNumber.ZERO;

        }

        public static LzrValue toArray(LzrValue[] args) {
            List<LzrValue> array_list = new ArrayList<>(queue);
            return new LzrArray(array_list);

        }

        public static LzrValue remove(LzrValue[] args) {
            Arguments.check(1,  args.length);
            queue.remove(args[0]);
            return LzrNumber.ZERO;
        }

        public static LzrValue sizeQueue(LzrValue[] args) {
            Arguments.check(1,  args.length);
            return new LzrNumber(queue.size());
        }



    }
    public static final class IntegerClass {

        public static LzrValue parseInt(LzrValue[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LzrNumber.of(Integer.parseInt(args[0].asString(), radix));

        }

        public static LzrValue compare(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.compare(args[0].asInt(),args[1].asInt()));

        }

        public static LzrValue bitCount(LzrValue[] args) {
            Arguments.check(1, args.length);

            return LzrNumber.of(Integer.bitCount(args[0].asInt()));

        }

        public static LzrValue signum(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Integer.signum(args[0].asInt()));

        }

        public static LzrValue decode(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Integer.decode(args[0].asString()));

        }

        public static LzrValue compareUnsigned(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.compareUnsigned(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue max(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.max(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue min(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.min(args[0].asInt(), args[1].asInt()));

        }
    }

    public static final class DoubleClass {
        public static LzrValue parseDouble(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Double.parseDouble(args[0].asString()));

        }
        public static LzrValue compare(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.compare(args[0].asInt(),args[1].asInt()));

        }
        public static LzrValue doubleToLongBits(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Double.doubleToLongBits(args[0].asNumber()));

        }
        public static LzrValue max(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.max(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue min(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.min(args[0].asInt(), args[1].asInt()));

        }
    }

    public static final class StringClass {

        public static LzrValue valueOf(LzrValue[] args) {
            Arguments.check(1, args.length);
            return new LzrString(String.valueOf(args[0]));

        }

        public static LzrValue format(LzrValue[] args) {
            Arguments.check(2, args.length);
            return new LzrString(String.format(args[0].asString(), args[1]));

        }

        public static LzrValue join(LzrValue[] args) {
            Arguments.check(2, args.length);
            return new LzrString(String.join((CharSequence) args[0], (CharSequence) args[1], (CharSequence) args[2]));

        }


    }

    private Function mapFunction(final Supplier<Map<LzrValue, LzrValue>> mapSupplier) {
        return (args) -> {
            Arguments.checkOrOr(0, 1, args.length);
            final Map<LzrValue, LzrValue> map = mapSupplier.get();
            if (args.length == 1) {
                if (args[0].type() == Types.MAP) {
                    map.putAll(((LzrMap) args[0]).getMap());
                } else {
                    throw new LZRException("TypeException ","Map expected in first argument");
                }
            }
            return new LzrMap(map);
        };
    }

    private Function sortedMapFunction(final Supplier<SortedMap<LzrValue, LzrValue>> mapSupplier,
                                       final java.util.function.Function<
                                               Comparator<? super LzrValue>,
                                               SortedMap<LzrValue, LzrValue>> comparatorToMapFunction) {
        return (args) -> {
            Arguments.checkRange(0, 2, args.length);
            final SortedMap<LzrValue, LzrValue> map;
            switch (args.length) {
                case 0: // treeMap()
                    map = mapSupplier.get();
                    break;
                case 1: // treeMap(map) || treeMap(comparator)
                    if (args[0].type() == Types.MAP) {
                        map = mapSupplier.get();
                        map.putAll(((LzrMap) args[0]).getMap());
                    } else if (args[0].type() == Types.FUNCTION) {
                        final Function comparator = ValueUtils.consumeFunction(args[0], 0);
                        map = comparatorToMapFunction.apply((o1, o2) -> comparator.execute(o1, o2).asInt());
                    } else {
                        throw new LZRException("TypeException ","Map or comparator function expected in first argument");
                    }
                    break;
                case 2: // treeMap(map, comparator)
                    if (args[0].type() != Types.MAP) {
                        throw new LZRException("TypeException ", "Map expected in first argument");
                    }
                    final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                    map = comparatorToMapFunction.apply((o1, o2) -> comparator.execute(o1, o2).asInt());
                    map.putAll(((LzrMap) args[0]).getMap());
                    break;
                default:
                    throw new IllegalStateException();
            }
            return new LzrMap(map);
        };
    }

}
