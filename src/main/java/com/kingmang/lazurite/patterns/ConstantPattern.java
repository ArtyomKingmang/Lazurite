package com.kingmang.lazurite.patterns;

import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class ConstantPattern extends Pattern implements Serializable {

    @Getter
    private LzrValue constant;

    @Override
    public String toString() {
        return constant.toString();
    }
}
