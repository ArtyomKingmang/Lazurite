package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrValue

data class ExprStatement(val expr: Expression) : InterruptableNode(), Expression, Statement {
    override fun execute() {
        super.interruptionCheck()
        this.expr.eval()
    }

    override fun eval(): LzrValue =
        this.expr.eval()

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        this.expr.toString()
}
