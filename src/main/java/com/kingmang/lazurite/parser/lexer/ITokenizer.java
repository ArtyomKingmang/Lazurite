package com.kingmang.lazurite.parser.lexer;

import com.kingmang.lazurite.parser.Token;

import java.util.List;

public interface ITokenizer {
    List<Token> tokenize();
}
