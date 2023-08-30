package com.kingmang.lazurite.modules.JSON;

import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.modules.Module;


public final class JSON implements Module {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        Functions.set("JSONDecode", new JSONDecode());
    }
}
