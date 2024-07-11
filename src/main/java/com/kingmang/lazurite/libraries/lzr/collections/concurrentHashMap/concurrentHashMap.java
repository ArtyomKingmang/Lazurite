package com.kingmang.lazurite.libraries.lzr.collections.concurrentHashMap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

import java.util.concurrent.ConcurrentHashMap;

import static com.kingmang.lazurite.libraries.lzr.collections.MapFunctions.mapFunction;

public class concurrentHashMap implements Library {
    @Override
    public void init() {
        Keyword.put("concurrentHashMap", mapFunction(ConcurrentHashMap::new));
    }
}
