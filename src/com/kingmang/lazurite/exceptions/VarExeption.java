package com.kingmang.lazurite.exceptions;

public final class VarExeption extends RuntimeException {

    private final String variable;

    public VarExeption(String variable) {
        super("Variable " + variable + " does not exists");
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}