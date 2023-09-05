package com.kingmang.lazurite.modules.time;


import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;


public final class time implements Module {


    @Override
    public void init() {
        KEYWORD.put("sleep", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                Thread.sleep((long) args[0].asNumber());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return NumberValue.MINUS_ONE;
        });

    }
}
