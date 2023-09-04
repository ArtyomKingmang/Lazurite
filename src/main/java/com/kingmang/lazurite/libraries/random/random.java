package com.kingmang.lazurite.libraries.random;


import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.libraries.libraries;


public final class random implements libraries {


    @Override
    public void init() {
        KEYWORD.put("random", new RAND());



    }
}
