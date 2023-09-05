package com.kingmang.lazurite.modules.random;


import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.modules.Module;


public final class random implements Module {


    @Override
    public void init() {
        KEYWORD.put("random", new RAND());



    }
}
