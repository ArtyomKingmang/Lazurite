package com.kingmang.lazurite.exceptions.parser

class ParseError(val line: Int, val exception: Exception) {
    override fun toString(): String =
        "ParseError on line $line: ${exception.message}";
}