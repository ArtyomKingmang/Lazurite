package com.kingmang.lazurite.modules.Arrays;
import com.kingmang.lazurite.lib.Functions;

import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.modules.std.LEN;


public final class Arrays implements Module {


    @Override
    public void init() {


        // Arrays and maps
        Functions.set("Array", new NEWARR());
        Functions.set("join", new JOIN());
        Functions.set("sort", new SORT());
        Functions.set("length", new LEN());

    }
}
