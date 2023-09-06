package com.kingmang.lazurite.modules.Colors;


import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;



public class Colors implements Module {

    public void initConstant(){
        Variables.set("RESET",new StringValue("\u001b[10m"));
        Variables.set("RED", new StringValue("\u001b[31m"));
        Variables.set("GREEN", new StringValue("\u001b[32m"));
        Variables.set("BLUE", new StringValue("\u001b[34m"));
        Variables.set("WHITE", new StringValue("\u001b[37m"));
        Variables.set("BLACK", new StringValue("\u001b[40m"));
        Variables.set("PURPLE", new StringValue("\u001b[35m"));
        Variables.set("PINK", new NumberValue(16761037));
        Variables.set("YELLOW", new StringValue("\u001b[33m"));
        Variables.set("CYAN",new StringValue("\u001b[36m"));
    }
    public void init() {
        initConstant();

    }

}