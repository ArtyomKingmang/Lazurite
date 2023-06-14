package com.kingmang.lazurite.modules.time;

import com.kingmang.lazurite.lib.Arguments;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

public final class SLEEP implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);
        
        try {
            Thread.sleep((long) args[0].asNumber());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return NumberValue.ZERO;
    }
}