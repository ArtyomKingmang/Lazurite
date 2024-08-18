package com.kingmang.lazurite.parser.tokens

import com.kingmang.lazurite.parser.IToken


data class Token(
    val type: TokenType,
    val text: String,
    val row: Int,
    val col: Int
) : IToken{
    override fun getType(): TokenType {
        return type
    }

    override fun getText(): String {
        return text
    }

    override fun getCol(): Int {
        return col
    }

    override fun getRow(): Int {
        return row
    }

    override fun toString(): String {
        return "${type.name}[$row, $col]" + text.takeIf { it.isNotBlank() }?.let { " \"$it\"" }.orEmpty()
    }
}
