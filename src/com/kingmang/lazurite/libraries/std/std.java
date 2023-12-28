package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

public class std implements Library {
    public static void initConstants() {

    }
    @Override
    public void init(){
        LZRMap std = new LZRMap(3);
        initConstants();
        std.set("toChar", new toChar());
        std.set("charAt", new charat());
        std.set("thread", new thread());
        Variables.define("std", std);

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
    public final class thread implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);

            Function body;
            if (args[0].type() == Types.FUNCTION) {
                body = ((LZRFunction) args[0]).getValue();
            } else {
                body = KEYWORD.get(args[0].asString());
            }

            // Сдвигаем аргументы
            final Value[] params = new Value[args.length - 1];
            if (params.length > 0) {
                System.arraycopy(args, 1, params, 0, params.length);
            }

            final Thread thread = new Thread(() -> body.execute(params));
            thread.setUncaughtExceptionHandler(Console::handleException);
            thread.start();
            return LZRNumber.ZERO;
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
