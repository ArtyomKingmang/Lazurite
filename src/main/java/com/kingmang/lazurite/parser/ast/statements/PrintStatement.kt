package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.console.Console
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

data class PrintStatement(val expression: Expression) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        Console.print(this.expression.eval().asString())
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        "print ${this.expression}"
}
