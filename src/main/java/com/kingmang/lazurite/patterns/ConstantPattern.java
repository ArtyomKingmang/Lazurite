package com.kingmang.lazurite.patterns;

import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ConstantPattern extends Pattern implements Serializable {

    private LzrValue constant;

    @Override
    public String toString() {
        return constant.toString();
    }
}
