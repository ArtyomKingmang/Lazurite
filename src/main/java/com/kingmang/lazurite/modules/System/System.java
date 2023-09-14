package com.kingmang.lazurite.modules.System;

import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;

import static com.kingmang.lazurite.Main.VERSION;

public class System implements Module {

    public void initConstant(){
        Variables.set("_LZRVersion_", new StringValue(VERSION()));
        Variables.set("_JVMVersion_", new StringValue(java.lang.System.getProperty("java.vm.version")));
        Variables.set("_UserDir_", new StringValue((java.lang.System.getProperty("user.dir"))));
        Variables.set("_OsName_", new StringValue((java.lang.System.getProperty("os.name"))));
        Variables.set("_UserName_", new StringValue((java.lang.System.getProperty("user.name"))));
        Variables.set("_OsArch_", new StringValue((java.lang.System.getProperty("os.arch"))));
        Variables.set("_OsVersion_", new StringValue((java.lang.System.getProperty("os.version"))));
        Variables.set("_FileSeparator_", new StringValue((java.lang.System.getProperty("file.separator"))));
        Variables.set("_PathSeparator_", new StringValue((java.lang.System.getProperty("path.separator"))));
        Variables.set("_LineSeparator_", new StringValue((java.lang.System.getProperty("line.separator"))));
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
            return NumberValue.MINUS_ONE;
        });

    }

}
