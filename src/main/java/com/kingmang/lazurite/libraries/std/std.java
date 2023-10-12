package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Module;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;

public class std implements Module {
    public static void initConstants() {

    }
    @Override
    public void init(){
        initConstants();
        KEYWORD.put("toChar", new toChar());
        KEYWORD.put("charAt", new charat());

    }
    private final class charat implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);

            final String input = args[0].asString();
            final int index = args[1].asInt();

            return LZRNumber.of((short)input.charAt(index));
        }
    }

    public final class toChar implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(1, args.length);
            return new LZRString(String.valueOf((char) args[0].asInt()));
        }
    }
}
