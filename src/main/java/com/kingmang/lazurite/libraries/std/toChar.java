package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;

public final class toChar implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);
        return new LZRString(String.valueOf((char) args[0].asInt()));
    }
}