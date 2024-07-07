package com.kingmang.lazurite.libraries.lzr.lang.String;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrString;

public class String implements Library {
    @Override
    public void init() {
        final LzrMap stringFunctions = new LzrMap(4);
        stringFunctions.set("valueOf", StringClass::valueOf);
        stringFunctions.set("format", StringClass::format);
        stringFunctions.set("join", StringClass::join);
        stringFunctions.set("CASE_INSENSITIVE_ORDER", new LzrString(java.lang.String.CASE_INSENSITIVE_ORDER.toString()));
        Variables.define("String", stringFunctions);

    }
}
