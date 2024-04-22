package com.kingmang.lazurite.libraries.colors;


import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.Types.LzrMap;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.runtime.Types.LzrString;


public class colors implements Library {

    public void initConstant(){
        LzrMap col = new LzrMap(9);
        col.set("reset",new LzrString("\u001b[10m"));
        col.set("red", new LzrString("\u001b[31m"));
        col.set("green", new LzrString("\u001b[32m"));
        col.set("blue", new LzrString("\u001b[34m"));
        col.set("white", new LzrString("\u001b[37m"));
        col.set("black", new LzrString("\u001b[40m"));
        col.set("purple", new LzrString("\u001b[35m"));
        col.set("yellow", new LzrString("\u001b[33m"));
        col.set("cyan",new LzrString("\u001b[36m"));
        col.set("clear", new Function() {
            @Override
            public LzrValue execute(LzrValue... args) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return LzrNumber.ZERO;
            }
        });
        Variables.define("color", col);
    }
    public void init() {
        initConstant();

    }

}