package com.kingmang.lazurite.exceptions.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public final class ParseError {
    private final int line;
    private final Exception exception;

    @Override
    public String toString() {
        return "ParseError on line " + line + ": " + exception.getMessage();
    }
}
