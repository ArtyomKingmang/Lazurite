package com.kingmang.lazurite.parser.parse;

import com.kingmang.lazurite.exceptions.parser.ParseErrors;
import com.kingmang.lazurite.parser.AST.Statements.Statement;

public interface Parser {
    Statement parse();
    ParseErrors getParseErrors();
}
