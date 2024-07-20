package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrMap

data class DestructuringAssignmentStatement(
    val variables: List<String?>,
    val containerExpression: Expression
) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        val container = this.containerExpression.eval()
        when (container.type()) {
            Types.ARRAY -> execute(container as LzrArray)
            Types.MAP -> execute(container as LzrMap)
        }
    }

    private fun execute(array: LzrArray) {
        this.variables.forEachIndexed { i, it -> it?.let { define(it, array[i]) } }
    }

    private fun execute(map: LzrMap) {
        var i = 0
        for ((key, value) in map) {
            val variable = this.variables[i]
            if (variable != null) {
                define(
                    variable, LzrArray(
                        arrayOf(key, value)
                    )
                )
            }
            i++
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("extract(")
        val it = this.variables.iterator()
        if (it.hasNext()) {
            var variable = it.next()
            sb.append(variable ?: "")
            while (it.hasNext()) {
                variable = it.next()
                sb.append(", ").append(variable ?: "")
            }
        }
        sb.append(") = ").append(containerExpression)
        return sb.toString()
    }
}
