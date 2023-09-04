package com.kingmang.lazurite.libraries.JSON;

import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.libraries.libraries;


public final class JSON implements libraries {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("JSONDecode", new JSONDecode());
    }
}
