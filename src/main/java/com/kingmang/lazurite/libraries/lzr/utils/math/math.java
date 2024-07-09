package com.kingmang.lazurite.libraries.lzr.utils.math;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;


public final class math implements Library {

    private static final DoubleFunction<LzrNumber> doubleToNumber = LzrNumber::of;

    public static void initConstants() {
        Variables.define("PI", LzrNumber.of(Math.PI));
        Variables.define("E", LzrNumber.of(Math.E));
    }

    @Override
    public void init() {
       initConstants();
        LzrMap ml = new LzrMap(9);
        ml.set("abs", math::abs);
        ml.set("acos", functionConvert(Math::acos));
        ml.set("asin", functionConvert(Math::asin));
        ml.set("atan", functionConvert(Math::atan));
        ml.set("atan2", biFunctionConvert(Math::atan2));
        ml.set("cbrt", functionConvert(Math::cbrt));
        ml.set("ceil", functionConvert(Math::ceil));
        ml.set("cos", functionConvert(Math::cos));
        ml.set("cosh", functionConvert(Math::cosh));
        ml.set("floor", functionConvert(Math::floor));
        ml.set("getExponent", math::getExponent);
        ml.set("hypot", biFunctionConvert(Math::hypot));
        ml.set("IEEEremainder", biFunctionConvert(Math::IEEEremainder));
        ml.set("log", functionConvert(Math::log));
        ml.set("log1p", functionConvert(Math::log1p));
        ml.set("log10", functionConvert(Math::log10));
        ml.set("max", math::max);
        ml.set("min", math::min);
        ml.set("pow", biFunctionConvert(Math::pow));
        ml.set("round", math::round);
        ml.set("signum", functionConvert(Math::signum, Math::signum));
        ml.set("sin", functionConvert(Math::sin));
        ml.set("sinh", functionConvert(Math::sinh));
        ml.set("sqrt", functionConvert(Math::sqrt));
        ml.set("tan", functionConvert(Math::tan));
        ml.set("tanh", functionConvert(Math::tanh));
        ml.set("toDegrees", functionConvert(Math::toDegrees));
        ml.set("toRadians", functionConvert(Math::toRadians));
        ml.set("ulp", functionConvert(Math::ulp, Math::ulp));
        Variables.define("math", ml);
    }

    private static LzrValue abs(LzrValue... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LzrNumber.of(Math.abs((double) raw));
        }
        if (raw instanceof Float) {
            return LzrNumber.of(Math.abs((float) raw));
        }
        if (raw instanceof Long) {
            return LzrNumber.of(Math.abs((long) raw));
        }
        return LzrNumber.of(Math.abs(args[0].asInt()));
    }

    private static LzrValue getExponent(LzrValue... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LzrNumber.of(Math.getExponent((float) raw));
        }
        return LzrNumber.of(Math.getExponent(args[0].asNumber()));
    }

    private static LzrValue max(LzrValue... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LzrNumber.of(Math.max((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return LzrNumber.of(Math.max((float) raw, ((LzrNumber) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return LzrNumber.of(Math.max((long) raw, ((LzrNumber) args[1]).asLong()));
        }
        return LzrNumber.of(Math.max(args[0].asInt(), args[1].asInt()));
    }

    private static LzrValue min(LzrValue... args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return LzrNumber.of(Math.min((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return LzrNumber.of(Math.min((float) raw, ((LzrNumber) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return LzrNumber.of(Math.min((long) raw, ((LzrNumber) args[1]).asLong()));
        }
        return LzrNumber.of(Math.min(args[0].asInt(), args[1].asInt()));
    }


    private static LzrValue round(LzrValue... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return LzrNumber.of(Math.round((float) raw));
        }
        return LzrNumber.of(Math.round(args[0].asNumber()));
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
                return LzrNumber.of(opFloat.apply((float) raw));
            }
            return LzrNumber.of(opDouble.applyAsDouble(args[0].asNumber()));
        };
    }

    private static Function biFunctionConvert(DoubleBinaryOperator op) {
        return args -> {
            Arguments.check(2, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber(), args[1].asNumber()));
        };
    }
}
