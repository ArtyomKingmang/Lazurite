package com.kingmang.lazurite.modules.Colors;


import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRString;


public class Colors implements Module {

    public void initConstant(){
        Variables.set("RESET",new LZRString("\u001b[10m"));
        Variables.set("RED", new LZRString("\u001b[31m"));
        Variables.set("GREEN", new LZRString("\u001b[32m"));
        Variables.set("BLUE", new LZRString("\u001b[34m"));
        Variables.set("WHITE", new LZRString("\u001b[37m"));
        Variables.set("BLACK", new LZRString("\u001b[40m"));
        Variables.set("PURPLE", new LZRString("\u001b[35m"));
        Variables.set("YELLOW", new LZRString("\u001b[33m"));
        Variables.set("CYAN",new LZRString("\u001b[36m"));
    }
    public void init() {

        initConstant();

    }

}