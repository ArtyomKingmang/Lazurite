package com.kingmang.lazurite.libraries.lzr.lang.Double;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;

public class Double implements Library {
    @Override
    public void init() {
        final LzrMap doubleFunctions = new LzrMap(7);
        doubleFunctions.set("max", DoubleClass::max);
        doubleFunctions.set("min", DoubleClass::min);
        doubleFunctions.set("doubleToLongBits", DoubleClass::doubleToLongBits);
        doubleFunctions.set("parseDouble", DoubleClass::parseDouble);
        doubleFunctions.set("compare", DoubleClass::compare);
        doubleFunctions.set("MAX_VALUE", new LzrNumber(java.lang.Double.MAX_VALUE));
        doubleFunctions.set("MIN_VALUE", new LzrNumber(java.lang.Double.MIN_VALUE));
        Variables.define("Double", doubleFunctions);
    }
}
