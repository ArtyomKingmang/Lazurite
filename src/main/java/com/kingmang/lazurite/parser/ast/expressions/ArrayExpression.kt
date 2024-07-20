package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrValue

data class ArrayExpression(val elements: List<Expression>) : Expression {
    override fun eval(): LzrValue =
        LzrArray(this.elements.size) { index: Int? -> this.elements[index!!].eval() }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        this.elements.toString()
}
