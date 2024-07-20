package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrFunction

data class FunctionReferenceExpression(val name: String) : InterruptableNode(), Expression {
    override fun eval(): LzrFunction {
        super.interruptionCheck()
        return LzrFunction(Keyword.get(this.name))
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        "::${this.name}"
}
