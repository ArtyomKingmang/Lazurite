package com.kingmang.lazurite.patterns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class VariablePattern extends Pattern implements Serializable {

    @Getter
    private String variable;

    @Override
    public String toString() {
        return variable;
    }
}
