package com.kingmang.lazurite.modules.time;


import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.modules.Module;


public final class time implements Module {



    @Override
    public void init() {
        Functions.set("sleep", new SLEEP());


    }
}
