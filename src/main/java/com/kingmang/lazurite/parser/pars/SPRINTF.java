package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;

public final class SPRINTF implements Function {

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
