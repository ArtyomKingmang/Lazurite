package com.kingmang.lazurite.parser.optimization;

import com.kingmang.lazurite.runtime.Value;

public final class VariableInfo {
    public Value value;
    int modifications;

    @Override
    public String toString() {
        return (value == null ? "?" : value) + " (" + modifications + " mods)";
    }
}