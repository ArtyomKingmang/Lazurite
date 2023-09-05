package com.kingmang.lazurite.modules.ML;
import com.kingmang.lazurite.base.*;
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
        KEYWORD.put("abs", ML::abs);
        KEYWORD.put("cos", functionConvert(Math::cos));
        KEYWORD.put("log", functionConvert(Math::log));
        KEYWORD.put("sin", functionConvert(Math::sin));
        KEYWORD.put("sqrt", functionConvert(Math::sqrt));
        KEYWORD.put("tan", functionConvert(Math::tan));
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
