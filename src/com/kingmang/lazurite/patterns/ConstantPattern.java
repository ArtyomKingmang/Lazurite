package com.kingmang.lazurite.patterns;

import com.kingmang.lazurite.runtime.Value;

import java.io.Serializable;

public class ConstantPattern extends Pattern implements Serializable {

    private Value constant;

    public ConstantPattern(Value constant) {
        this.constant = constant;
    }

    public Value getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return constant.toString();
    }
}
