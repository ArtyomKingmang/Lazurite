package com.kingmang.lazurite.parser.parse

import lombok.Getter


class Token(
    @field:Getter val type: TokenType,
    @field:Getter val text: String,
    @field:Getter val row: Int,
    @field:Getter val col: Int
) {
    fun position(): String {
        return "[$row $col]"
    }

    override fun toString(): String {
        return type.name + " " + position() + " " + text
    }
}
