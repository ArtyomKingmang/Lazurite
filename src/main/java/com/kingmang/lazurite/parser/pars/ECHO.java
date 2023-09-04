package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

public final class ECHO implements Function {

    @Override
    public Value execute(Value... args) {
        final StringBuilder sb = new StringBuilder();
        for (Value arg : args) {
            sb.append(arg.asString());
            sb.append(" ");
        }
        Console.println(sb.toString());
        return NumberValue.ZERO;
    }
}
