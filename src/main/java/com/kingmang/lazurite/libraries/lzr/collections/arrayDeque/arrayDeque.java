package com.kingmang.lazurite.libraries.lzr.collections.arrayDeque;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;

public class arrayDeque implements Library {
    @Override
    public void init() {
        final LzrMap arrayDeque = new LzrMap(5);
        arrayDeque.set("new", LzrArrayDeque::newArrayDeque);
        arrayDeque.set("add", LzrArrayDeque::addToQueue);
        arrayDeque.set("remove", LzrArrayDeque::remove);
        arrayDeque.set("size", LzrArrayDeque::sizeQueue);
        arrayDeque.set("toArray", LzrArrayDeque::toArray);
        Variables.define("arrayDeque", arrayDeque);
    }
}
