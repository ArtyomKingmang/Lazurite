package com.kingmang.lazurite.libraries.lzr.collections.hashMap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

import java.util.HashMap;

import static com.kingmang.lazurite.libraries.lzr.collections.MapFunctions.mapFunction;

public class hashMap implements Library {
    @Override
    public void init() {
        Keyword.put("hashMap", mapFunction(HashMap::new));
    }
}
