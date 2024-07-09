package com.kingmang.lazurite.parser


data class Token(
    val type: TokenType,
    val text: String,
    val row: Int,
    val col: Int
) {
    override fun toString(): String {
        return "${type.name}[$row, $col]" + text.takeIf { it.isNotBlank() }?.let { " \"$it\"" }.orEmpty()
    }
}
