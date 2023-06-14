package com.kingmang.lazurite.modules.Arrays;

import com.kingmang.lazurite.lib._AExeption;
import com.kingmang.lazurite.lib._TExeprion;
import com.kingmang.lazurite.lib.*;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.runtime.Value;


import java.util.Arrays;

public final class SORT implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkAtLeast(1, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new _TExeprion("Array expected in first argument");
        }
        final Value[] elements = ((ArrayValue) args[0]).getCopyElements();
        
        switch (args.length) {
            case 1:
                Arrays.sort(elements);
                break;
            case 2:
                final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                Arrays.sort(elements, (o1, o2) -> comparator.execute(o1, o2).asInt());
                break;
            default:
                throw new _AExeption("Wrong number of arguments");
        }
        
        return new ArrayValue(elements);
    }
    
}
