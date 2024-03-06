package com.kingmang.lazurite.exceptions;

import com.kingmang.lazurite.console.Console;
import lombok.Getter;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public final class VariableDoesNotExistsException extends RuntimeException {

    @Getter
    private final String variable;

    public VariableDoesNotExistsException(String variable) {
        super("Variable" + variable + "does not exists");
        PrettyException.message("VariableDoesNotExistsException","Variable " + variable + " does not exists" );
        this.variable = variable;

    }

}