package com.kingmang.lazurite.parser.pars;


public final class Token {

    private final TokenType type;
    private final String text;
    private final int row, col;
    
    public Token(TokenType type, String text, int row, int col) {
        this.type = type;
        this.text = text;
        this.row = row;
        this.col = col;
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}
