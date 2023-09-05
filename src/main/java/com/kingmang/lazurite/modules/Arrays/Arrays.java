package com.kingmang.lazurite.modules.Arrays;
import com.kingmang.lazurite.base.*;

import com.kingmang.lazurite.modules.Module;


public final class Arrays implements Module {


    @Override
    public void init() {

        KEYWORD.put("Array", new NEWARR());
        KEYWORD.put("join", new JOIN());
        KEYWORD.put("sort", new SORT());

    }


}
