package com.kingmang.lazurite.parser.AST

import com.kingmang.lazurite.parser.AST.Expressions.Expression

class Argument(
    val name: String,
    val valueExpr: Expression? = null
) {

    override fun toString(): String {
        val valueStr = valueExpr?.let { " = $it" }.orEmpty()
        return "$name$valueStr"
    }
}
