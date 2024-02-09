package com.kingmang.lazurite.parser.parse;


import lombok.Getter;

public final class Token {

    @Getter
    private final TokenType type;
    @Getter
    private final String text;
    @Getter
    private final int row, col;
    
    public Token(com.kingmang.lazurite.parser.parse.TokenType type, String text, int row, int col) {
        this.type = type;
        this.text = text;
        this.row = row;
        this.col = col;
    }

    
    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}
