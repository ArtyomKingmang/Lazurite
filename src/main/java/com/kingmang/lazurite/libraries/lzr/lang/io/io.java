package com.kingmang.lazurite.libraries.lzr.lang.io;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;


public class io implements Library {


    @Override
    public void init() {
        final LzrMap ps = new LzrMap(5);
        final LzrMap io = new LzrMap(10);
        final LzrMap bais = new LzrMap(4);
        final LzrMap bis = new LzrMap(2);

        bais.set("new", new LzrByteArrayInputStream.newByteArrayInputStream());
        bais.set("available", new LzrByteArrayInputStream.availableByteArrayInputStream());
        bais.set("readNBytes", new LzrByteArrayInputStream.readNBytesByteArrayInputStream());
        bais.set("read", new LzrByteArrayInputStream.readByteArrayInputStream());

        ps.set("new", new LzrPrintStream.newPrintStream());
        ps.set("print", new LzrPrintStream.PrintStreamOut());

        io.set("ByteArrayInputStream", bais);
        io.set("PrintStream", ps);
        Variables.define("io", io);

    }

}
