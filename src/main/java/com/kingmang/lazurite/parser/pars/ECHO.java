package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
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
        return LZRNumber.ZERO;
    }
}
