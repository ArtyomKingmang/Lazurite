package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.lib.Arguments;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.lib.Types;
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
        return new StringValue(String.format(format, values));
    }
}
