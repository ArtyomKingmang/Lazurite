package com.kingmang.lazurite.libraries.lzr.collections.treeMap;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.kingmang.lazurite.libraries.lzr.collections.MapFunctions.sortedMapFunction;

public class treeMap implements Library {
    @Override
    public void init() {
        Keyword.put("treeMap", sortedMapFunction(TreeMap::new, TreeMap::new));
    }
}
