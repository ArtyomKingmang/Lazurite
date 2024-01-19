package com.kingmang.lazurite.exceptions;

public final class VariableDoesNotExistsException extends RuntimeException {

    private final String variable;

    public VariableDoesNotExistsException(String variable) {
        super("Variable " + variable + " does not exists");
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}