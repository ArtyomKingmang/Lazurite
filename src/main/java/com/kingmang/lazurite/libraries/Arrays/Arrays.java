package com.kingmang.lazurite.libraries.Arrays;
import com.kingmang.lazurite.base.*;

import com.kingmang.lazurite.libraries.libraries;


public final class Arrays implements libraries {


    @Override
    public void init() {

        KEYWORD.put("Array", new NEWARR());
        KEYWORD.put("join", new JOIN());
        KEYWORD.put("sort", new SORT());

    }


}
