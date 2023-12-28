package com.kingmang.lazurite.libraries.time;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;


public final class time implements Library {

    public void initConstant(){

    }
    @Override
    public void init() {
        initConstant();
        LZRMap time = new LZRMap(1);
        time.set("sleep", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                Thread.sleep((long) args[0].asNumber());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return LZRNumber.MINUS_ONE;
        });
        Variables.define("time", time);


    }
}
