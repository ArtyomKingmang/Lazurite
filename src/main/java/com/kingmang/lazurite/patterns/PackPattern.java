package com.kingmang.lazurite.patterns;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PackPattern extends Pattern {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
