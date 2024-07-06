package com.kingmang.lazurite.libraries.lzr.utils.stringBuffer;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.libraries.lzr.utils.std.std;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;

public class stringBuffer implements Library {
    @Override
    public void init() {
        final LzrMap stringBuffer = new LzrMap(5);
        stringBuffer.set("append", StringBufferMethods::addToBuffer);
        stringBuffer.set("new", StringBufferMethods::newBuffer);
        stringBuffer.set("delete", StringBufferMethods::deleteBuffer);
        stringBuffer.set("toStr", StringBufferMethods::toStrBuffer);
        stringBuffer.set("deleteCharAt", StringBufferMethods::deleteCharAtBuffer);
        Variables.define("stringBuffer", stringBuffer);
    }
}
