package com.kingmang.lazurite.parser

import com.kingmang.lazurite.parser.tokens.Token

interface ILexer {
    fun tokenize(): List<Token>
}
