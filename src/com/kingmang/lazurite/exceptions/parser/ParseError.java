package com.kingmang.lazurite.exceptions.parser;

public final class ParseError {

    private final int line;
    private final Exception exception;

    public ParseError(int line, Exception exception) {
        this.line = line;
        this.exception = exception;
    }

    public int getLine() {
        return line;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "ParseError on line " + line + ": " + exception.getMessage();
    }
}
