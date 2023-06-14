package com.kingmang.lazurite.modules.random;


import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.modules.Module;


public final class random implements Module {


    @Override
    public void init() {
        Functions.set("random", new RAND());



    }
}
