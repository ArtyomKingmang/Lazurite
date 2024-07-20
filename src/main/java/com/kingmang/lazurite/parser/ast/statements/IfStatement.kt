package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

data class IfStatement(
    val expression: Expression,
    val ifStatement: Statement,
    val elseStatement: Statement?
) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        val result = this.expression.eval().asInt()
        if (result != 0) {
            this.ifStatement.execute()
        } else this.elseStatement?.execute()
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R? {
        return visitor.visit(this, t)
    }

    override fun toString(): String =
        StringBuilder().run {
            this.append("if ").append(expression).append(' ').append(ifStatement)
            elseStatement?.let { this.append("\nelse ").append(it) }
            return this.toString()
        }
}
