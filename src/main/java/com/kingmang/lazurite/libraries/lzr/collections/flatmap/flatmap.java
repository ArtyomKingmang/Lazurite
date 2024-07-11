package com.kingmang.lazurite.libraries.lzr.collections.flatmap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

public class flatmap implements Library {
    @Override
    public void init() {
        Keyword.put("flatmap", new LzrFlatmap());
    }
}
