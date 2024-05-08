package com.kingmang.lazurite.exceptions;

import lombok.Getter;

public final class VariableDoesNotExistsException extends RuntimeException {

    @Getter
    private final String variable;

    public VariableDoesNotExistsException(String variable) {
        super("Variable" + variable + "does not exists");
        //PrettyException.message(true,"VariableDoesNotExistsException","Variable " + variable + " does not exists" );
        this.variable = variable;

    }

}