package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.lib.*;
import com.kingmang.lazurite.runtime.*;


public final class LEN implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);

        final Value val = args[0];
        final int length;
        switch (val.type()) {
            case Types.ARRAY:
                length = ((ArrayValue) val).size();
                break;
            case Types.MAP:
                length = ((MapValue) val).size();
                break;
            case Types.STRING:
                length = ((StringValue) val).length();
                break;
            case Types.FUNCTION:
                final Function func = ((FunctionValue) val).getValue();
                if (func instanceof UserDefinedFunction) {
                    length = ((UserDefinedFunction) func).getArgsCount();
                } else {
                    length = 0;
                }
                break;
            default:
                length = 0;
                
        }
        return NumberValue.of(length);
    }
}