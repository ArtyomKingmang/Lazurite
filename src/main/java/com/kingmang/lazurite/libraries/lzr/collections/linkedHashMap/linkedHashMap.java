package com.kingmang.lazurite.libraries.lzr.collections.linkedHashMap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

import java.util.LinkedHashMap;

import static com.kingmang.lazurite.libraries.lzr.collections.MapFunctions.mapFunction;

public class linkedHashMap implements Library {
    @Override
    public void init() {
        Keyword.put("linkedHashMap", mapFunction(LinkedHashMap::new));
    }
}
