package com.kingmang.lazurite.patterns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class VariablePattern extends Pattern implements Serializable {

    private String variable;

    @Override
    public String toString() {
        return variable;
    }
}
