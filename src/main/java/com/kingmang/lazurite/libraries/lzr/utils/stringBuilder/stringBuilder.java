package com.kingmang.lazurite.libraries.lzr.utils.stringBuilder;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.libraries.lzr.utils.std.std;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;

public class stringBuilder implements Library {
    @Override
    public void init() {
        final LzrMap stringBuilder = new LzrMap(5);
        stringBuilder.set("append", StringBuilderMethods::addToBuilder);
        stringBuilder.set("new", StringBuilderMethods::newBuilder);
        stringBuilder.set("delete", StringBuilderMethods::deleteBuilder);
        stringBuilder.set("toStr", StringBuilderMethods::toStrBuilder);
        stringBuilder.set("deleteCharAt", StringBuilderMethods::deleteCharAtBuilder);
        Variables.define("stringBuilder", stringBuilder);
    }
}
