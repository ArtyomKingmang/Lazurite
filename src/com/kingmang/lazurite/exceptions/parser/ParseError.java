package com.kingmang.lazurite.exceptions.parser;

import lombok.Getter;

public final class ParseError {

    @Getter
    private final int line;
    @Getter
    private final Exception exception;

    public ParseError(int line, Exception exception) {
        this.line = line;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ParseError on line " + line + ": " + exception.getMessage();
    }
}
