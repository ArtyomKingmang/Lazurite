package com.kingmang.lazurite.exceptions.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record ParseError(int line, Exception exception) {
    @Override
    public String toString() {
        return "ParseError on line " + line + ": " + exception.getMessage();
    }
}
