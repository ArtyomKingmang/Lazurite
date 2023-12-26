package com.kingmang.lazurite.libraries.time;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;


public final class time implements Library {

    public void initConstant(){

    }
    @Override
    public void init() {
        initConstant();
        KEYWORD.put("sleep", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                Thread.sleep((long) args[0].asNumber());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return LZRNumber.MINUS_ONE;
        });


    }
}
