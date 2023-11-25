package com.kingmang.lazurite.parser.pars;


public enum TokenType {
    //KEYWORDS
    THROW,
    PRINT,
    PRINTLN,
    IF,
    ELSE,
    WHILE,
    FOR,
    BREAK,
    CONTINUE,
    FUNC,
    RETURN,
    USING,
    SWITCH,
    CASE,
    INCLUDE,
    CLASS,
    NEW,
    ENUM,

    MINUSGT,//->

    EQ, // =
    EQEQ, // ==
    EXCL, // !
    EXCLEQ, // !=
    LTEQ, // <=
    LT, // <
    GT, // >
    GTEQ, // >=
    
    PLUSEQ, // +=
    MINUSEQ, // -=
    STAREQ, // *=
    SLASHEQ, // /=
    PERCENTEQ, // %=
    ATEQ, // @=
    AMPEQ, // &=
    CARETEQ, // ^=
    BAREQ, // |=
    COLONCOLONEQ, // ::=
    LTLTEQ, // <<=
    GTGTEQ, // >>=
    GTGTGTEQ, // >>>=
    
    PLUSPLUS, // ++
    MINUSMINUS, // --
    
    LTLT, // <<
    GTGT, // >>
    GTGTGT, // >>>

    DOTDOT, // ..
    STARSTAR, // **
    QUESTIONCOLON, // ?:
    QUESTIONQUESTION, // ??
    
    TILDE, // ~
    CARET, // ^
    CARETCARET, // ^^
    BAR, // |
    BARBAR, // ||
    AMP, // &
    AMPAMP, // &&
    
    QUESTION, // ?
    COLON, // :
    COLONCOLON, // ::

    LPAREN, // (
    RPAREN, // )
    LBRACKET, // [
    RBRACKET, // ]
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    DOT, // .

    NUMBER,
    HEX_NUMBER,
    WORD,
    TEXT,


    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /
    PERCENT,// %
    AT, // @

    EOF
}
