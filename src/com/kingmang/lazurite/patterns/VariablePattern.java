package com.kingmang.lazurite.patterns;

import java.io.Serializable;

public class VariablePattern extends Pattern implements Serializable {

    private String variable;

    public VariablePattern(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return variable;
    }
}
