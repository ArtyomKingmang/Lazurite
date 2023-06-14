package com.kingmang.lazurite.lib;

public final class _VarExeption extends RuntimeException {
    
    private final String variable;

    public _VarExeption(String variable) {
        super("Variable " + variable + " does not exists");
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}
