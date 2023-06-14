package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.lib.Arguments;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.runtime.Value;

public final class SUBSTR implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final String input = args[0].asString();
        final int startIndex = args[1].asInt();
        
        String result;
        if (args.length == 2) {
            result = input.substring(startIndex);
        } else {
            final int endIndex = args[2].asInt();
            result = input.substring(startIndex, endIndex);
        }
        
        return new StringValue(result);
    }
}
