package com.kingmang.lazurite.libraries.time;

import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.base.Function;
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