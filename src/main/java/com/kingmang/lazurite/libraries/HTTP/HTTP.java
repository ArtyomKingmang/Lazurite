package com.kingmang.lazurite.libraries.HTTP;

import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.libraries.libraries;


public final class HTTP implements libraries {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("URLEncode", new URLEncode());
        KEYWORD.put("HTTP", new http_http());
    }
}
