package com.kingmang.lazurite.parser;

import com.kingmang.lazurite.exceptions.parser.ParseErrors;
import com.kingmang.lazurite.parser.ast.statements.Statement;

public interface IParser {
    Statement parse();

    ParseErrors getParseErrors();
}
