package com.kingmang.lazurite.libraries.string;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;



public class string implements Library {
    @Override
    public void init() {
        Keyword.put("toUpperCase", new toUpperCase());
        Keyword.put("toLowerCase", new toLowerCase());
    }

    public final class toLowerCase implements Function{


        @Override
        public Value execute(Value... args) {
            Arguments.check(1,args.length);
            return new LZRString(args[0].asString().toLowerCase());
        }
    }
    public final class toUpperCase implements Function{

        @Override
        public Value execute(Value... args) {
            Arguments.check(1,args.length);
            return new LZRString(args[0].asString().toUpperCase());
        }
    }
}
