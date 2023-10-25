package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.core.ValueUtils;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import com.kingmang.lazurite.runtime.Value;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Standart {

    public static final class ECHO implements Function {

        @Override
        public Value execute(Value... args) {
            final StringBuilder sb = new StringBuilder();
            for (Value arg : args) {
                sb.append(arg.asString());
                sb.append(" ");
            }
            Console.println(sb.toString());
            return LZRNumber.ZERO;
        }
    }

    public static final class INPUT implements Function {
        @Override
        public Value execute(Value... args) {
            Scanner sc = new Scanner(System.in);
            return new LZRString(sc.nextLine());
        }
    }

    public static final class string {

        private string() { }

        static LZRArray getBytes(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
            try {
                return LZRArray.of(args[0].asString().getBytes(charset));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            }
        }

    }

    public static final class LEN implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(1, args.length);

            final Value val = args[0];
            final int length;
            switch (val.type()) {
                case Types.ARRAY:
                    length = ((LZRArray) val).size();
                    break;
                case Types.MAP:
                    length = ((LZRMap) val).size();
                    break;
                case Types.STRING:
                    length = ((LZRString) val).length();
                    break;
                case Types.FUNCTION:
                    final Function func = ((LZRFunction) val).getValue();
                    if (func instanceof UserDefinedFunction) {
                        length = ((UserDefinedFunction) func).getArgsCount();
                    } else {
                        length = 0;
                    }
                    break;
                default:
                    length = 0;

            }
            return LZRNumber.of(length);
        }
    }

    public static final class SPRINTF implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);

            final String format = args[0].asString();
            final Object[] values = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                values[i - 1] = (args[i].type() == Types.NUMBER)
                        ? args[i].raw()
                        : args[i].asString();
            }
            return new LZRString(String.format(format, values));
        }
    }

    public static final class SUBSTR implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final int startIndex = args[1].asInt();

            String result;
            if (args.length == 2) {
                result = input.substring(startIndex);
            } else {
                final int endIndex = args[2].asInt();
                result = input.substring(startIndex, endIndex);
            }

            return new LZRString(result);
        }
    }

    public static final class PARSE {
        private PARSE() { }

        static Value parseInt(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Integer.parseInt(args[0].asString(), radix));
        }
        static Value parseLong(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Long.parseLong(args[0].asString(), radix));
        }
    }

    public static final class FOREACH implements Function {

        private static final int UNKNOWN = -1;

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            final Value container = args[0];
            final Function consumer = ValueUtils.consumeFunction(args[1], 1);
            final int argsCount;
            if (consumer instanceof UserDefinedFunction) {
                argsCount = ((UserDefinedFunction) consumer).getArgsCount();
            } else {
                argsCount = UNKNOWN;
            }

            switch (container.type()) {
                case Types.STRING:
                    final LZRString string = (LZRString) container;
                    if (argsCount == 2) {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LZRString(String.valueOf(ch)), LZRNumber.of(ch));
                        }
                    } else {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LZRString(String.valueOf(ch)));
                        }
                    }
                    return string;

                case Types.ARRAY:
                    final LZRArray array = (LZRArray) container;
                    if (argsCount == 2) {
                        int index = 0;
                        for (Value element : array) {
                            consumer.execute(element, LZRNumber.of(index++));
                        }
                    } else {
                        for (Value element : array) {
                            consumer.execute(element);
                        }
                    }
                    return array;

                case Types.MAP:
                    final LZRMap map = (LZRMap) container;
                    for (Map.Entry<Value, Value> element : map) {
                        consumer.execute(element.getKey(), element.getValue());
                    }
                    return map;

                default:
                    throw new LZRExeption("TypeExeption ","Cannot iterate " + Types.typeToString(container.type()));
            }
        }
    }

    public final static class FLATMAP implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRExeption("TypeExeption ", "Array expected in first argument");
            }
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return flatMapArray((LZRArray) args[0], mapper);
        }

        private Value flatMapArray(LZRArray array, Function mapper) {
            final List<Value> values = new ArrayList<>();
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final Value inner = mapper.execute(array.get(i));
                if (inner.type() != Types.ARRAY) {
                    throw new LZRExeption("TypeExeption ", "Array expected " + inner);
                }
                for (Value value : (LZRArray) inner) {
                    values.add(value);
                }
            }
            return new LZRArray(values);
        }
    }
    public static final class FILTER implements Function {

        private final boolean takeWhile;

        public FILTER(boolean takeWhile) {
            this.takeWhile = takeWhile;
        }

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            final Value container = args[0];
            final Function predicate = ValueUtils.consumeFunction(args[1], 1);
            if (container.type() == Types.ARRAY) {
                return filterArray((LZRArray) container, predicate, takeWhile);
            }

            if (container.type() == Types.MAP) {
                return filterMap((LZRMap) container, predicate, takeWhile);
            }

            throw new LZRExeption("TypeExeption", "Invalid first argument. Array or map expected");
        }

        private Value filterArray(LZRArray array, Function predicate, boolean takeWhile) {
            final int size = array.size();
            final List<Value> values = new ArrayList<>(size);
            for (Value value : array) {
                if (predicate.execute(value) != LZRNumber.ZERO) {
                    values.add(value);
                } else if (takeWhile) break;
            }
            final int newSize = values.size();
            return new LZRArray(values.toArray(new Value[newSize]));
        }

        private Value filterMap(LZRMap map, Function predicate, boolean takeWhile) {
            final LZRMap result = new LZRMap(map.size());
            for (Map.Entry<Value, Value> element : map) {
                if (predicate.execute(element.getKey(), element.getValue()) != LZRNumber.ZERO) {
                    result.set(element.getKey(), element.getValue());
                } else if (takeWhile) break;
            }
            return result;
        }
    }

    public static final class split implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final String regex = args[1].asString();
            final int limit = (args.length == 3) ? args[2].asInt() : 0;

            final String[] parts = input.split(regex, limit);
            return LZRArray.of(parts);
        }
    }
}
