package com.kingmang.lazurite.parser;

import com.kingmang.lazurite.parser.tokens.Token;

import java.util.List;

public interface ILexer {
    List<Token> tokenize();
}
