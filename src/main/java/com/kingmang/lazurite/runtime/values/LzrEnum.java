package com.kingmang.lazurite.runtime.values;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class LzrEnum implements LzrValue {

    private final Map<String, LzrString> enums;

    public LzrEnum(Map<String, LzrString> enums) {
        this.enums = enums;
    }

    public LzrString get(String enm) {
        return enums.get(enm);
    }

    @Override
    public Object raw() {
        return enums;
    }

    @Override
    public int asInt() {
        return enums.size();
    }

    @Override
    public double asNumber() {
        return enums.size();
    }

    @Override
    public String asString() {
        return enums.toString();
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        return 0;
    }
}
