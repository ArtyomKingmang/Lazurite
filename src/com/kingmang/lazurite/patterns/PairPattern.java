package com.kingmang.lazurite.patterns;

import lombok.Getter;

import java.io.Serializable;

public class PairPattern extends Pattern implements Serializable {

    @Getter
    private String left, right;

    public PairPattern(String left, String right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String toString() {
        return left + " | " + right;
    }
}
