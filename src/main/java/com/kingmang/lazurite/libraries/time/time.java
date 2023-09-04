package com.kingmang.lazurite.libraries.time;


import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.libraries.libraries;



public final class time implements libraries {


    @Override
    public void init() {
        KEYWORD.put("sleep", new SLEEP());


    }
}
