package com.kingmang.lazurite.parser

import com.kingmang.lazurite.exceptions.parser.ParseErrors
import com.kingmang.lazurite.parser.ast.statements.Statement

interface IParser {
    fun parse(): Statement

    val parseErrors: ParseErrors
}
