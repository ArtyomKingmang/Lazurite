package com.kingmang.lazurite.libraries.system;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import static com.kingmang.lazurite.runner.Exe.VERSION;

public class system implements Library {

    public void init (){

        LZRMap values = new LZRMap(5);
        LZRMap system = new LZRMap(7);

        //variables
        values.set("LZRVersion", new LZRString(VERSION()));
        values.set("JVMVersion", new LZRString(java.lang.System.getProperty("java.vm.version")));
        values.set("name", new LZRString(java.lang.System.getProperty("os.name")));
        values.set("arch", new LZRString(java.lang.System.getProperty("os.arch")));
        values.set("version", new LZRString(java.lang.System.getProperty("os.version")));

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

        system.set("getProperty", (Value...args) -> new LZRString(System.getProperty(args[0].asString())));
        system.set("os", values);
        Variables.define("system",system);

    }

}
