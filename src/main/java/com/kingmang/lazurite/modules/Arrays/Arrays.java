package com.kingmang.lazurite.modules.Arrays;
import com.kingmang.lazurite.lib.KEYWORD;

import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.parser.pars.LEN;


public final class Arrays implements Module {


    @Override
    public void init() {


        // Arrays and maps
        KEYWORD.put("Array", new NEWARR());
        KEYWORD.put("join", new JOIN());
        KEYWORD.put("sort", new SORT());
        KEYWORD.put("length", new LEN());

    }
}
