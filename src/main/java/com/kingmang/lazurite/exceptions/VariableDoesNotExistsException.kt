package com.kingmang.lazurite.exceptions;

import lombok.Getter;

@Getter
public final class VariableDoesNotExistsException extends LzrException {

    private final String variable;

    public VariableDoesNotExistsException(String variable) {
        super("VariableDoesNotExistsException", "Variable: \"" + variable + "\" does not exists");
        this.variable = variable;
    }

}