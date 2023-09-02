package com.kingmang.lazurite.modules.time;


import com.kingmang.lazurite.lib.KEYWORD;
import com.kingmang.lazurite.modules.Module;



public final class time implements Module {


    @Override
    public void init() {
        KEYWORD.put("sleep", new SLEEP());


    }
}
