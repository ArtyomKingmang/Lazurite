package com.kingmang.lazurite.patterns;

import java.io.Serializable;

public class ConsPattern extends Pattern implements Serializable {

    private String left, right;

    public ConsPattern(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " | " + right;
    }
}
