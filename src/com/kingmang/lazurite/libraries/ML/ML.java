package com.kingmang.lazurite.libraries.ML;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
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
        LZRMap ml = new LZRMap(9);
        ml.set("abs", ML::abs);
        ml.set("acos", functionConvert(Math::acos));
        ml.set("asin", functionConvert(Math::asin));
        ml.set("atan", functionConvert(Math::atan));
        ml.set("atan2", biFunctionConvert(Math::atan2));
        ml.set("cbrt", functionConvert(Math::cbrt));
        ml.set("ceil", functionConvert(Math::ceil));
        ml.set("copySign", ML::copySign);
        ml.set("cos", functionConvert(Math::cos));
        ml.set("cosh", functionConvert(Math::cosh));
        ml.set("exp", functionConvert(Math::exp));
        ml.set("expm1", functionConvert(Math::expm1));
        ml.set("floor", functionConvert(Math::floor));
        ml.set("getExponent", ML::getExponent);
        ml.set("hypot", biFunctionConvert(Math::hypot));
        ml.set("IEEEremainder", biFunctionConvert(Math::IEEEremainder));
        ml.set("log", functionConvert(Math::log));
        ml.set("log1p", functionConvert(Math::log1p));
        ml.set("log10", functionConvert(Math::log10));
        ml.set("max", ML::max);
        ml.set("min", ML::min);
        ml.set("nextAfter", ML::nextAfter);
        ml.set("nextUp", functionConvert(Math::nextUp, Math::nextUp));
        ml.set("nextDown", functionConvert(Math::nextDown, Math::nextDown));
        ml.set("pow", biFunctionConvert(Math::pow));
        ml.set("rint", functionConvert(Math::rint));
        ml.set("round", ML::round);
        ml.set("signum", functionConvert(Math::signum, Math::signum));
        ml.set("sin", functionConvert(Math::sin));
        ml.set("sinh", functionConvert(Math::sinh));
        ml.set("sqrt", functionConvert(Math::sqrt));
        ml.set("tan", functionConvert(Math::tan));
        ml.set("tanh", functionConvert(Math::tanh));
        ml.set("toDegrees", functionConvert(Math::toDegrees));
        ml.set("toRadians", functionConvert(Math::toRadians));
        ml.set("ulp", functionConvert(Math::ulp, Math::ulp));
        Variables.define("ML", ml);
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
