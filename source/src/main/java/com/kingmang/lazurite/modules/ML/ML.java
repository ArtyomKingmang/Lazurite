package com.kingmang.lazurite.modules.ML;
import com.kingmang.lazurite.lib.*;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;



public final class ML implements Module {

    private static final DoubleFunction<NumberValue> doubleToNumber = NumberValue::of;

    public static void initConstants() {
        Variables.define("PI", NumberValue.of(Math.PI));

    }

    @Override
    public void init() {
        initConstants();
        Functions.set("abs", ML::abs);
        Functions.set("cos", functionConvert(Math::cos));
        Functions.set("log", functionConvert(Math::log));
        Functions.set("sin", functionConvert(Math::sin));
        Functions.set("sqrt", functionConvert(Math::sqrt));
        Functions.set("tan", functionConvert(Math::tan));
    }

    private static Value abs(Value... args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return NumberValue.of(Math.abs((double) raw));
        }
        if (raw instanceof Float) {
            return NumberValue.of(Math.abs((float) raw));
        }
        if (raw instanceof Long) {
            return NumberValue.of(Math.abs((long) raw));
        }
        return NumberValue.of(Math.abs(args[0].asInt()));
    }




    private static Function functionConvert(DoubleUnaryOperator op) {
        return args -> {
            Arguments.check(1, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber()));
        };
    }

}
