package com.kingmang.lazurite.patterns;

import com.kingmang.lazurite.runtime.Value;
import lombok.Getter;

import java.io.Serializable;

public class ConstantPattern extends Pattern implements Serializable {

    @Getter
    private Value constant;

    public ConstantPattern(Value constant) {
        this.constant = constant;
    }


    @Override
    public String toString() {
        return constant.toString();
    }
}
