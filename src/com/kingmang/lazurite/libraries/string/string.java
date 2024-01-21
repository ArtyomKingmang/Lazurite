package com.kingmang.lazurite.libraries.string;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

public final class string implements Library {
    @Override
    public void init() {
        LZRMap base = new LZRMap(2);
        base.set("split", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(2, args.length);
                final String input = args[0].asString();
                final String sep = args[1].asString();
                LZRMap out = new LZRMap(128);
                String[] split = input.split(sep);
                for(int i = 0; i < split.length; i++){
                    out.set(new LZRNumber(i), new LZRString(split[i]));
                }
                return out;
            }
        });

        base.set("replace", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(3, args.length);
                final String input = args[0].asString();
                final String of = args[1].asString();
                final String to = args[2].asString();
                return new LZRString(input.replace(of, to));
            }
        });

        base.set("repeat", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(2, args.length);
                final String input = args[0].asString();
                final int count = args[1].asInt();
                return new LZRString(input.repeat(count));
            }
        });

        base.set("substring", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(3, args.length);
                final String input = args[0].asString();
                final int begin = args[1].asInt();
                final int end = args[2].asInt();
                return new LZRString(input.substring(begin, end));
            }
        });

        Variables.define("string", base);
    }
}
