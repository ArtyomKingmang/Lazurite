package com.kingmang.lazurite.runtime.values;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LzrNull implements LzrValue {


    @Nullable
    @Override
    public Object raw() {
        return null;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asNumber() {
        return 0;
    }

    @NotNull
    @Override
    public String asString() {
        return "null";
    }

    @NotNull
    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 482862660;
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        if (o.raw() == null) return 0;
        return -1;
    }

    @Override
    public String toString() {
        return asString();
    }
}