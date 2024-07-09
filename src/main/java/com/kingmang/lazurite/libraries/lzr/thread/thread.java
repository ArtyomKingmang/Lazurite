package com.kingmang.lazurite.libraries.lzr.thread;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

public class thread implements Library {
    @Override
    public void init() {
        Keyword.put("thread", new LzrThread());
    }
}
