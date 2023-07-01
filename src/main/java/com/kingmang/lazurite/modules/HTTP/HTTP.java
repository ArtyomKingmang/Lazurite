package com.kingmang.lazurite.modules.HTTP;

import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.modules.Module;


public final class HTTP implements Module {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        Functions.set("URLEncode", new URLEncode());
        Functions.set("HTTP", new http_http());
    }
}
