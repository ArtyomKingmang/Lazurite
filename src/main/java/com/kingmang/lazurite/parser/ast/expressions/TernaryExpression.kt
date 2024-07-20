package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrValue

data class TernaryExpression(
    val condition: Expression,
    val trueExpr: Expression,
    val falseExpr: Expression
) : Expression {
    override fun eval(): LzrValue =
        if (condition.eval().asInt() != 0)
            trueExpr.eval()
        else
            falseExpr.eval()

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        String.format("%s ? %s : %s", condition, trueExpr, falseExpr)
}
