package com.kingmang.lazurite.libraries.system;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import java.util.Objects;

import static com.kingmang.lazurite.runner.Exe.VERSION;

public class system implements Library {

    public void init (){
        LZRMap system = new LZRMap(7);

        //functions
        system.set("currentTimeMillis", (Value...args) ->{
            Arguments.check(0, args.length);
            return LZRNumber.of(System.currentTimeMillis());
        });
        system.set("nanoTime", (Value...args) ->{
            Arguments.check(0, args.length);
            return LZRNumber.of(System.nanoTime());
        });
        system.set("exit", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                java.lang.System.exit((int) args[0].asNumber());
            } finally {
                Thread.currentThread().interrupt();
            }
            return LZRNumber.MINUS_ONE;
        });
        system.set("getProperty", (Value...args) -> {
            if(Objects.equals(args[0].asString(), "lzr.version")){
                return new LZRString(VERSION());
            }
            return new LZRString(System.getProperty(args[0].asString()));

        });

        Variables.define("system",system);

    }

}
