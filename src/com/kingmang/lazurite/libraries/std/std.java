package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.runtime.Lzr.*;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.utils.ValueUtils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class std implements Library {

    static PrintStream printStreamm;
    @Override
    public void init(){
        LzrMap std = new LzrMap(3);
        LzrMap integerFunctions = new LzrMap(8);
        LzrMap doubleFunctions = new LzrMap(5);
        LzrMap stringFunctions = new LzrMap(3);

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

        Variables.define("Double", doubleFunctions);
        Variables.define("String", stringFunctions);
        Variables.define("Integer", integerFunctions);
        Variables.define("std", std);

    }


    public final static class flatmap implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRException("TypeExeption ", "Array expected in first argument");
            }
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return flatMapArray((LzrArray) args[0], mapper);
        }

        private Value flatMapArray(LzrArray array, Function mapper) {
            final List<Value> values = new ArrayList<>();
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final Value inner = mapper.execute(array.get(i));
                if (inner.type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ", "Array expected " + inner);
                }
                for (Value value : (LzrArray) inner) {
                    values.add(value);
                }
            }
            return new LzrArray(values);
        }
    }
    public final class thread implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);

            Function body;
            if (args[0].type() == Types.FUNCTION) {
                body = ((LzrFunction) args[0]).getValue();
            } else {
                body = Keyword.get(args[0].asString());
            }

            final Value[] params = new Value[args.length - 1];
            if (params.length > 0) {
                System.arraycopy(args, 1, params, 0, params.length);
            }

            final Thread thread = new Thread(() -> body.execute(params));
            thread.setUncaughtExceptionHandler(Console::handleException);
            thread.start();
            return LzrNumber.ZERO;
        }
    }

    public static final class IntegerClass {

        public static Value parseInt(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LzrNumber.of(Integer.parseInt(args[0].asString(), radix));

        }

        public static Value compare(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.compare(args[0].asInt(),args[1].asInt()));

        }

        public static Value bitCount(Value[] args) {
            Arguments.check(1, args.length);

            return LzrNumber.of(Integer.bitCount(args[0].asInt()));

        }

        public static Value signum(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Integer.signum(args[0].asInt()));

        }

        public static Value decode(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Integer.decode(args[0].asString()));

        }

        public static Value compareUnsigned(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.compareUnsigned(args[0].asInt(), args[1].asInt()));

        }

        public static Value max(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.max(args[0].asInt(), args[1].asInt()));

        }

        public static Value min(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Integer.min(args[0].asInt(), args[1].asInt()));

        }
    }

    public static final class DoubleClass {
        public static Value parseDouble(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Double.parseDouble(args[0].asString()));

        }
        public static Value compare(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.compare(args[0].asInt(),args[1].asInt()));

        }
        public static Value doubleToLongBits(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Double.doubleToLongBits(args[0].asNumber()));

        }
        public static Value max(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.max(args[0].asInt(), args[1].asInt()));

        }

        public static Value min(Value[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(Double.min(args[0].asInt(), args[1].asInt()));

        }
    }

    public static final class StringClass {

        public static Value valueOf(Value[] args) {
            Arguments.check(1, args.length);
            return new LzrString(String.valueOf(args[0]));

        }

        public static Value format(Value[] args) {
            Arguments.check(2, args.length);
            return new LzrString(String.format(args[0].asString(), args[1]));

        }

        public static Value join(Value[] args) {
            Arguments.check(2, args.length);
            return new LzrString(String.join((CharSequence) args[0], (CharSequence) args[1], (CharSequence) args[2]));

        }


    }

    /*

    public static Value parseFloat(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Float.parseFloat(args[0].asString()));
        }

        public static Value parseByte(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Byte.parseByte(args[0].asString()));
        }

        public static Value parseShort(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Short.parseShort(args[0].asString()));
        }

        static Value parseDouble(Value[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(Double.parseDouble(args[0].asString()));
        }
        public static Value parseLong(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LzrNumber.of(Long.parseLong(args[0].asString(), radix));
        }
    }
     */


}
