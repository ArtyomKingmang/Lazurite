package com.kingmang.lazurite.libraries.ML;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;


public final class ML implements Library {

    private static final DoubleFunction<LZRNumber> doubleToNumber = LZRNumber::of;

    public static void initConstants() {
        Variables.define("PI", LZRNumber.of(Math.PI));
        Variables.define("E", LZRNumber.of(Math.E));
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("abs", ML::abs);
        KEYWORD.put("acos", functionConvert(Math::acos));
        KEYWORD.put("asin", functionConvert(Math::asin));
        KEYWORD.put("atan", functionConvert(Math::atan));
        KEYWORD.put("atan2", biFunctionConvert(Math::atan2));
        KEYWORD.put("cbrt", functionConvert(Math::cbrt));
        KEYWORD.put("ceil", functionConvert(Math::ceil));
        KEYWORD.put("copySign", ML::copySign);
        KEYWORD.put("cos", functionConvert(Math::cos));
        KEYWORD.put("cosh", functionConvert(Math::cosh));
        KEYWORD.put("exp", functionConvert(Math::exp));
        KEYWORD.put("expm1", functionConvert(Math::expm1));
        KEYWORD.put("floor", functionConvert(Math::floor));
        KEYWORD.put("getExponent", ML::getExponent);
        KEYWORD.put("hypot", biFunctionConvert(Math::hypot));
        KEYWORD.put("IEEEremainder", biFunctionConvert(Math::IEEEremainder));
        KEYWORD.put("log", functionConvert(Math::log));
        KEYWORD.put("log1p", functionConvert(Math::log1p));
        KEYWORD.put("log10", functionConvert(Math::log10));
        KEYWORD.put("max", ML::max);
        KEYWORD.put("min", ML::min);
        KEYWORD.put("nextAfter", ML::nextAfter);
        KEYWORD.put("nextUp", functionConvert(Math::nextUp, Math::nextUp));
        KEYWORD.put("nextDown", functionConvert(Math::nextDown, Math::nextDown));
        KEYWORD.put("pow", biFunctionConvert(Math::pow));
        KEYWORD.put("rint", functionConvert(Math::rint));
        KEYWORD.put("round", ML::round);
        KEYWORD.put("signum", functionConvert(Math::signum, Math::signum));
        KEYWORD.put("sin", functionConvert(Math::sin));
        KEYWORD.put("sinh", functionConvert(Math::sinh));
        KEYWORD.put("sqrt", functionConvert(Math::sqrt));
        KEYWORD.put("tan", functionConvert(Math::tan));
        KEYWORD.put("tanh", functionConvert(Math::tanh));
        KEYWORD.put("toDegrees", functionConvert(Math::toDegrees));
        KEYWORD.put("toRadians", functionConvert(Math::toRadians));
        KEYWORD.put("ulp", functionConvert(Math::ulp, Math::ulp));
    }

    private static Value abs(Value... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LZRNumber.of(Math.abs((double) raw));
        }
        if (raw instanceof Float) {
            return LZRNumber.of(Math.abs((float) raw));
        }
        if (raw instanceof Long) {
            return LZRNumber.of(Math.abs((long) raw));
        }
        return LZRNumber.of(Math.abs(args[0].asInt()));
    }

    private static Value copySign(Value... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LZRNumber.of(Math.copySign((float) raw, ((LZRNumber) args[1]).asFloat()));
        }
        return LZRNumber.of(Math.copySign(args[0].asNumber(), args[1].asNumber()));
    }

    private static Value getExponent(Value... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LZRNumber.of(Math.getExponent((float) raw));
        }
        return LZRNumber.of(Math.getExponent(args[0].asNumber()));
    }

    private static Value max(Value... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LZRNumber.of(Math.max((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return LZRNumber.of(Math.max((float) raw, ((LZRNumber) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return LZRNumber.of(Math.max((long) raw, ((LZRNumber) args[1]).asLong()));
        }
        return LZRNumber.of(Math.max(args[0].asInt(), args[1].asInt()));
    }

    private static Value min(Value... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LZRNumber.of(Math.min((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return LZRNumber.of(Math.min((float) raw, ((LZRNumber) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return LZRNumber.of(Math.min((long) raw, ((LZRNumber) args[1]).asLong()));
        }
        return LZRNumber.of(Math.min(args[0].asInt(), args[1].asInt()));
    }

    private static Value nextAfter(Value... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LZRNumber.of(Math.nextAfter((float) raw, args[1].asNumber()));
        }
        return LZRNumber.of(Math.nextAfter(args[0].asNumber(), args[1].asNumber()));
    }

    private static Value round(Value... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LZRNumber.of(Math.round((float) raw));
        }
        return LZRNumber.of(Math.round(args[0].asNumber()));
    }


    private static Function functionConvert(DoubleUnaryOperator op) {
        return args -> {
            Arguments.check(1, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber()));
        };
    }

    private static Function functionConvert(DoubleUnaryOperator opDouble, UnaryOperator<Float> opFloat) {
        return args -> {
            Arguments.check(1, args.length);
            final Object raw = args[0].raw();
            if (raw instanceof Float) {
                return LZRNumber.of(opFloat.apply((float) raw));
            }
            return LZRNumber.of(opDouble.applyAsDouble(args[0].asNumber()));
        };
    }

    private static Function biFunctionConvert(DoubleBinaryOperator op) {
        return args -> {
            Arguments.check(2, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber(), args[1].asNumber()));
        };
    }
}
