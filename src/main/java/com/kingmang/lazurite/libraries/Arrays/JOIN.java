package com.kingmang.lazurite.libraries.Arrays;

import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.runtime.Value;


public final class JOIN implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkRange(1, 4, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new _TExeprion("Array expected in first argument");
        }
        
        final ArrayValue array = (ArrayValue) args[0];
        switch (args.length) {
            case 1:
                return ArrayValue.joinToString(array, "", "", "");
            case 2:
                return ArrayValue.joinToString(array, args[1].asString(), "", "");
            case 3:
                return ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
            case 4:
                return ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
            default:
                throw new _AExeption("Wrong number of arguments");
        }
    }
}
