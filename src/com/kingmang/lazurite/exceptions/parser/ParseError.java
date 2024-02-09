package com.kingmang.lazurite.exceptions.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public final class ParseError {
    @Getter
    private final int line;
    @Getter
    private final Exception exception;

    @Override
    public String toString() {
        return "ParseError on line " + line + ": " + exception.getMessage();
    }
}
