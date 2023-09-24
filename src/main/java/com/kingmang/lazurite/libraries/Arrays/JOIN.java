package com.kingmang.lazurite.libraries.Arrays;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.Value;


public final class JOIN implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkRange(1, 4, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new LZRExeption("TypeExeption ","Array expected in first argument");
        }
        
        final LZRArray array = (LZRArray) args[0];
        switch (args.length) {
            case 1:
                return LZRArray.joinToString(array, "", "", "");
            case 2:
                return LZRArray.joinToString(array, args[1].asString(), "", "");
            case 3:
                return LZRArray.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
            case 4:
                return LZRArray.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
            default:
                throw new LZRExeption("ArgumentsMismatchException ","Wrong number of arguments");
        }
    }
}
