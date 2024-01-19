package com.kingmang.lazurite.patterns;

public class PackPattern extends Pattern {

    private String name;

    public PackPattern(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
