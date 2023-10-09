package com.kingmang.lazurite.libraries.std;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.ValueUtils;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class sync implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);

        final BlockingQueue<Value> queue = new LinkedBlockingQueue<>(2);
        final Function synchronizer = (sArgs) -> {
            try {
                queue.put(sArgs[0]);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return LZRNumber.ZERO;
        };
        final Function callback = ValueUtils.consumeFunction(args[0], 0);
        callback.execute(new LZRFunction(synchronizer));

        try {
            return queue.take();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
    }

}