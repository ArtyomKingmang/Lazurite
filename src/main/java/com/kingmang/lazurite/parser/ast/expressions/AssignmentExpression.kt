package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.parser.ast.Accessible
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrValue

data class AssignmentExpression(
    val operation: BinaryExpression.Operator?,
    val target: Accessible,
    val expression: Expression
) : InterruptableNode(), Expression, Statement {
    override fun execute() {
        eval()
    }

    override fun eval(): LzrValue {
        super.interruptionCheck()
        if (operation == null)
            return target.set(expression.eval())
        val expr1: Expression = ValueExpression(target.get())
        val expr2: Expression = ValueExpression(expression.eval())
        return target.set(BinaryExpression(operation, expr1, expr2).eval())
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        String.format("%s %s= %s", target,  if ((operation == null)) "" else operation.toString(), expression)
}
