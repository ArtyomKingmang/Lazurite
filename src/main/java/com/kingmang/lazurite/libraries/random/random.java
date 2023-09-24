package com.kingmang.lazurite.libraries.random;


import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Module;


public final class random implements Module {


    @Override
    public void init() {
        KEYWORD.put("random", new RAND());



    }
}
