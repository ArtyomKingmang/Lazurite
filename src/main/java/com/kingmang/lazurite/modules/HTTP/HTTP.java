package com.kingmang.lazurite.modules.HTTP;

import com.kingmang.lazurite.lib.KEYWORD;
import com.kingmang.lazurite.modules.Module;


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
