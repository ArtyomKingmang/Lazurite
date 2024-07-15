package com.kingmang.lazurite.libraries.lzr.utils.time;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;


public final class time implements Library {

    public void initConstant(){

    }
    @Override
    public void init() {
        initConstant();
        LzrMap time = new LzrMap(1);
        time.set("sleep", (@NotNull LzrValue... args) -> {
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
