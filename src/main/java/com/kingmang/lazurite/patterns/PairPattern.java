package com.kingmang.lazurite.patterns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class PairPattern extends Pattern implements Serializable {

    private String left, right;

    @Override
    public String toString() {
        return left + " | " + right;
    }
}
