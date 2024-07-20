package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

data class MapExpression(val elements: Map<Expression, Expression>) :
    Expression {
    override fun eval(): LzrValue =
        LzrMap(this.elements.size).apply {
            elements.forEach { (key, value) -> this[key.eval()] = value.eval() }
        }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('{')
        val it = elements.entries.iterator()
        if (it.hasNext()) {
            var entry = it.next()
            sb.append(entry.key).append(" : ").append(entry.value)
            while (it.hasNext()) {
                entry = it.next()
                sb.append(", ")
                sb.append(entry.key).append(" : ").append(entry.value)
            }
        }
        sb.append('}')
        return sb.toString()
    }
}
