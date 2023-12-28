package com.kingmang.lazurite.libraries.system;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import static com.kingmang.lazurite.Main.VERSION;

public class system implements Library {

    public void initConstant(){

        LZRMap values = new LZRMap(5);
        values.set("LZRVersion", new LZRString(VERSION()));
        values.set("JVMVersion", new LZRString(java.lang.System.getProperty("java.vm.version")));
        values.set("name", new LZRString(java.lang.System.getProperty("os.name")));
        values.set("arch", new LZRString(java.lang.System.getProperty("os.arch")));
        values.set("version", new LZRString(java.lang.System.getProperty("os.version")));

        LZRMap sep = new LZRMap(5);
        sep.set("file", new LZRString(java.lang.System.getProperty("file.separator")));
        sep.set("path", new LZRString(java.lang.System.getProperty("path.separator")));
        sep.set("line", new LZRString(java.lang.System.getProperty("line.separator")));

        LZRMap user = new LZRMap(5);
        user.set("dir", new LZRString(java.lang.System.getProperty("user.dir")));
        user.set("name", new LZRString(java.lang.System.getProperty("user.name")));

        Variables.define("os", values);
        Variables.define("separator", sep);
        Variables.define("user", user);

    }

    public void init (){
        initConstant();

        KEYWORD.put("exit", (Value... args) -> {
            Arguments.check(1, args.length);
            try {
                java.lang.System.exit((int) args[0].asNumber());
            } finally {
                Thread.currentThread().interrupt();
            }
            return LZRNumber.MINUS_ONE;
        });

    }

}
