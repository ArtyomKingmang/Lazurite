package com.kingmang.lazurite.libraries.HTTP;

import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;


public final class HTTP implements Library {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("URLEncode", new URLEncode());
        KEYWORD.put("HTTP", new http_http());
    }
}
