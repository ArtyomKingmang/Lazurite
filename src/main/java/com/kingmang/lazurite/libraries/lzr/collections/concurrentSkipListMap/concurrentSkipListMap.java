package com.kingmang.lazurite.libraries.lzr.collections.concurrentSkipListMap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

import java.util.concurrent.ConcurrentSkipListMap;

import static com.kingmang.lazurite.libraries.lzr.collections.MapFunctions.sortedMapFunction;

public class concurrentSkipListMap implements Library {
    @Override
    public void init() {
        Keyword.put("concurrentSkipListMap", sortedMapFunction(ConcurrentSkipListMap::new, ConcurrentSkipListMap::new));
    }
}
