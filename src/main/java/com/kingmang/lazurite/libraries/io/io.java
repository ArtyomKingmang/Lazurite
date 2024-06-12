package com.kingmang.lazurite.libraries.io;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;


public class io implements Library {


    @Override
    public void init() {
        final LzrMap ps = new LzrMap(5);
        final LzrMap io = new LzrMap(10);
        ps.set("new", new LzrPrintStream.newPrintStream());
        ps.set("print", new LzrPrintStream.PrintStreamOut());

        io.set("PrintStream", ps);
        Variables.define("io", io);

    }

}
