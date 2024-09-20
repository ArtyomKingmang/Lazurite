package com.kingmang.lazurite.parser.tokens

enum class TokenType {
    //KEYWORDS
    ASSERT,
    DO,
    ENUM,
    MACRO,
    TRY,
    CATCH,
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
    CLASS,
    NEW,

    LONG_NUM,
    INT_NUM,
    DOUBLE_NUM,
    FLOAT_NUM,
    BYTE_NUM,
    SHORT_NUM,

    NUMBER,
    HEX_NUMBER,
    WORD,
    TEXT,

    PLUS,  // +
    MINUS,  // -
    STAR,  // *
    SLASH,  // /
    PERCENT,  // %
    AT,  // @

    EQ,  // =
    EQEQ,  // ==
    EXCL,  // !
    EXCLEQ,  // !=
    LTEQ,  // <=
    LT,  // <
    GT,  // >
    GTEQ,  // >=

    PLUSEQ,  // +=
    MINUSEQ,  // -=
    STAREQ,  // *=
    SLASHEQ,  // /=
    PERCENTEQ,  // %=
    ATEQ,  // @=
    AMPEQ,  // &=
    CARETEQ,  // ^=
    BAREQ,  // |=
    COLONCOLONEQ,  // ::=
    LTLTEQ,  // <<=
    GTGTEQ,  // >>=
    GTGTGTEQ,  // >>>=

    PLUSPLUS,  // ++
    MINUSMINUS,  // --

    LTLT,  // <<
    GTGT,  // >>
    GTGTGT,  // >>>

    DOTDOT,  // ..
    STARSTAR,  // **
    QUESTIONCOLON,  // ?:
    QUESTIONQUESTION,  // ??

    TILDE,  // ~
    CARET,  // ^
    CARETCARET,  // ^^
    BAR,  // |
    BARBAR,  // ||
    AMP,  // &
    AMPAMP,  // &&

    QUESTION,  // ?
    COLON,  // :
    COLONCOLON,  // ::

    LPAREN,  // (
    RPAREN,  // )
    LBRACKET,  // [
    RBRACKET,  // ]
    LBRACE,  // {
    RBRACE,  // }
    COMMA,  // ,
    DOT,  // .

    EOF // end of file
}
