package com.kingmang.lazurite.parser.parse;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Token {

    @Getter
    private final TokenType type;
    @Getter
    private final String text;
    @Getter
    private final int row, col;
    
    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}
