package com.kingmang.lazurite.patterns;

import lombok.Getter;

import java.io.Serializable;

public class VariablePattern extends Pattern implements Serializable {

    @Getter
    private String variable;

    public VariablePattern(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable;
    }
}
