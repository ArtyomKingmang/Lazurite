package com.kingmang.lazurite.libraries.std;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

public class std implements Library {
    public static void initConstants() {

    }
    @Override
    public void init(){
        LZRMap std = new LZRMap(3);
        initConstants();
        std.set("flatmap", new flatmap());
        std.set("thread", new thread());
        Variables.define("std", std);

    }
    public final static class flatmap implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRException("TypeExeption ", "Array expected in first argument");
            }
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return flatMapArray((LZRArray) args[0], mapper);
        }

        private Value flatMapArray(LZRArray array, Function mapper) {
            final List<Value> values = new ArrayList<>();
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final Value inner = mapper.execute(array.get(i));
                if (inner.type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ", "Array expected " + inner);
                }
                for (Value value : (LZRArray) inner) {
                    values.add(value);
                }
            }
            return new LZRArray(values);
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
                body = Keyword.get(args[0].asString());
            }

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

}
