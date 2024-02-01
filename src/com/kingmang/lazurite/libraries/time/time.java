package com.kingmang.lazurite.libraries.time;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;


public final class time implements Library {

    public void initConstant(){

    }
    @Override
    public void init() {
        initConstant();
        LzrMap time = new LzrMap(1);
        time.set("sleep", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                Thread.sleep((long) args[0].asNumber());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return LzrNumber.MINUS_ONE;
        });
        Variables.define("time", time);


    }
}
