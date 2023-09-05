package com.kingmang.lazurite.modules.JSON;

import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.modules.Module;


public final class JSON implements Module {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("JSONDecode", new JSONDecode());
    }
}
