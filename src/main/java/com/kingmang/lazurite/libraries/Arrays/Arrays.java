package com.kingmang.lazurite.libraries.Arrays;
import com.kingmang.lazurite.core.*;

import com.kingmang.lazurite.libraries.Module;


public final class Arrays implements Module {


    @Override
    public void init() {
        KEYWORD.put("Array", new NEWARR());
        KEYWORD.put("join", new JOIN());
        KEYWORD.put("sort", new SORT());

    }


}
