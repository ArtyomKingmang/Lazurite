package com.kingmang.lazurite.libraries.lzr.utils.thread;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import kotlin.Pair;

import java.util.HashMap;
import java.util.Map;

public class thread implements Library {
    @Override
    public void init() {
        Keyword.put("thread", new LzrThread());
    }

    @Override
    public Map<String, Pair<Integer, Function>> provides() {
        Map<String, Pair<Integer, Function>> map = new HashMap<>();
        map.put("thread", new Pair<>(Types.NUMBER, new LzrThread()));
        return map;
    }
}
