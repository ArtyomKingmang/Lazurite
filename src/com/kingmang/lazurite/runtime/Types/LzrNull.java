package com.kingmang.lazurite.runtime.Types;

import com.kingmang.lazurite.runtime.LzrValue;

public class LzrNull implements LzrValue {


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

    @Override
    public String asString() {
        return "null";
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 482862660;
    }

    @Override
    public int compareTo(LzrValue o) {
        if (o.raw() == null) return 0;
        return -1;
    }

    @Override
    public String toString() {
        return asString();
    }
}