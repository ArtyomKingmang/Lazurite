package com.kingmang.lazurite.libraries.HTTP;

import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Module;


public final class HTTP implements Module {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("URLEncode", new URLEncode());
        KEYWORD.put("HTTP", new http_http());
    }
}
