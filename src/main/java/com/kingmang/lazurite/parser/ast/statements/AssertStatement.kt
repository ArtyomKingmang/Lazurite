package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

data class AssertStatement(val expression: Expression) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        if (this.expression.eval().asInt() != 1) {
            throw LzrException("AssertionError", "Assertion failed: ${this.expression}")
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        "assert ${this.expression}"
}
