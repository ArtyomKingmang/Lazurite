package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.*;


public final class LEN implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);

        final Value val = args[0];
        final int length;
        switch (val.type()) {
            case Types.ARRAY:
                length = ((LZRArray) val).size();
                break;
            case Types.MAP:
                length = ((LZRMap) val).size();
                break;
            case Types.STRING:
                length = ((LZRString) val).length();
                break;
            case Types.FUNCTION:
                final Function func = ((LZRFunction) val).getValue();
                if (func instanceof UserDefinedFunction) {
                    length = ((UserDefinedFunction) func).getArgsCount();
                } else {
                    length = 0;
                }
                break;
            default:
                length = 0;
                
        }
        return LZRNumber.of(length);
    }
}