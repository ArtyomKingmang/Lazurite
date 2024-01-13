package com.kingmang.lazurite.libraries.system;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import static com.kingmang.lazurite.runner.Exe.VERSION;

public class system implements Library {

    public void initConstant(){

        LZRMap values = new LZRMap(5);
        LZRMap system = new LZRMap(6);
        values.set("LZRVersion", new LZRString(VERSION()));
        values.set("JVMVersion", new LZRString(java.lang.System.getProperty("java.vm.version")));
        values.set("name", new LZRString(java.lang.System.getProperty("os.name")));
        values.set("arch", new LZRString(java.lang.System.getProperty("os.arch")));
        values.set("version", new LZRString(java.lang.System.getProperty("os.version")));

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


        LZRMap sep = new LZRMap(5);
        sep.set("file", new LZRString(java.lang.System.getProperty("file.separator")));
        sep.set("path", new LZRString(java.lang.System.getProperty("path.separator")));
        sep.set("line", new LZRString(java.lang.System.getProperty("line.separator")));

        LZRMap user = new LZRMap(5);
        user.set("dir", new LZRString(java.lang.System.getProperty("user.dir")));
        user.set("name", new LZRString(java.lang.System.getProperty("user.name")));

        system.set("os", values);
        system.set("os", values);
        system.set("separator", sep);
        system.set("user", user);

        Variables.define("system",system);

    }

    public void init (){
        initConstant();

    }

}
