package com.kingmang.lazurite.libraries;

import com.kingmang.lazurite.core.Function;
import kotlin.Pair;

import java.util.Map;

public interface Library {
    void init();

    default Map<String, Pair<Integer, Function>> provides() {
        throw new UnsupportedOperationException();
    }
}
