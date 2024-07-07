package com.kingmang.lazurite.libraries.lzr.lang.Integer;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;

public class Integer implements Library {
    @Override
    public void init() {
        final LzrMap integerFunctions = new LzrMap(10);
        integerFunctions.set("bitCount", IntegerClass::bitCount);
        integerFunctions.set("max", IntegerClass::max);
        integerFunctions.set("min", IntegerClass::min);
        integerFunctions.set("compare", IntegerClass::compare);
        integerFunctions.set("parseInt", IntegerClass::parseInt);
        integerFunctions.set("decode", IntegerClass::decode);
        integerFunctions.set("signum", IntegerClass::signum);
        integerFunctions.set("compareUnsigned", IntegerClass::compareUnsigned);
        integerFunctions.set("MAX_VALUE", new LzrNumber(java.lang.Integer.MAX_VALUE));
        integerFunctions.set("MIN_VALUE", new LzrNumber(java.lang.Integer.MIN_VALUE));
        Variables.define("Integer", integerFunctions);
    }
}
