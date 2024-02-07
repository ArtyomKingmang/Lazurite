package com.kingmang.lazurite.parser.parse;

import com.kingmang.lazurite.exceptions.parser.ParseErrors;
import com.kingmang.lazurite.parser.AST.Statements.Statement;

import java.util.HashMap;
import java.util.List;

public interface Parser {
    Statement parse(List<Token> tokens);

    ParseErrors getParseErrors();
}
