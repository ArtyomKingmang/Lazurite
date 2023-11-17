package com.kingmang.lazurite.libraries.Colors;


import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRString;


public class Colors implements Library {

    public void initConstant(){
        LZRMap col = new LZRMap(9);
        col.set("reset",new LZRString("\u001b[10m"));
        col.set("red", new LZRString("\u001b[31m"));
        col.set("green", new LZRString("\u001b[32m"));
        col.set("blue", new LZRString("\u001b[34m"));
        col.set("white", new LZRString("\u001b[37m"));
        col.set("black", new LZRString("\u001b[40m"));
        col.set("purple", new LZRString("\u001b[35m"));
        col.set("yellow", new LZRString("\u001b[33m"));
        col.set("cyan",new LZRString("\u001b[36m"));
        Variables.define("color", col);
    }
    public void init() {
        initConstant();

    }

}