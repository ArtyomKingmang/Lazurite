package com.kingmang.lazurite.parser.parse;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Token {

    @Getter
    public final TokenType type;
    @Getter
    public final String text;
    @Getter
    public final int row, col;
    
    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}
