package com.kingmang.lazurite.parser.ast

import com.kingmang.lazurite.parser.ast.expressions.Expression

data class Argument(
    val name: String,
    val valueExpr: Expression? = null
) {
    override fun toString(): String =
        "${this.name}${this.valueExpr?.let { " = $it" }.orEmpty()}"
}
