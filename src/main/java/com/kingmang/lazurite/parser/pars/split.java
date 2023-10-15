package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.Value;

public final class split implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);

        final String input = args[0].asString();
        final String regex = args[1].asString();
        final int limit = (args.length == 3) ? args[2].asInt() : 0;

        final String[] parts = input.split(regex, limit);
        return LZRArray.of(parts);
    }
}