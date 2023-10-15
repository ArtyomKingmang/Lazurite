package com.kingmang.lazurite.libraries.random;


import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;


public final class random implements Library {


    @Override
    public void init() {
        KEYWORD.put("random", new RAND());



    }
}
