package com.kingmang.lazurite.patterns.visitor

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.ast.Node
import com.kingmang.lazurite.parser.ast.expressions.BinaryExpression
import com.kingmang.lazurite.parser.ast.expressions.ConditionalExpression
import com.kingmang.lazurite.parser.ast.expressions.UnaryExpression
import com.kingmang.lazurite.parser.ast.expressions.ValueExpression
import com.kingmang.lazurite.parser.ast.expressions.VariableExpression
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.parser.ast.statements.UsingStatement
import com.kingmang.lazurite.runtime.values.LzrNumber
import java.io.IOException


object VisitorUtils {
    @JvmStatic
    fun isValue(node: Node?): Boolean {
        return (node is ValueExpression)
    }

    @JvmStatic
    fun isVariable(node: Node?): Boolean {
        return (node is VariableExpression)
    }
    @JvmStatic
    fun loadLzrProgram(s: UsingStatement): Statement? {
        if (!isValue(s)) return null
        return try {
            s.loadLzrLibrary(s.expression.eval().asString())
        } catch (ex: IOException) {
            null
        }
    }
    @JvmStatic
    fun isIntegerValue(node: Node, valueToCheck: Int): Boolean {
        if (!isValue(node)) return false

        val value = (node as ValueExpression).value
        if (value.type() != Types.NUMBER) return false

        val number = (value as LzrNumber).raw()
        if ((number is Int) || (number is Short) || (number is Byte)) {
            return number.toInt() == valueToCheck
        }
        return false
    }
    @JvmStatic
    fun isValueAsInt(node: Node, valueToCheck: Int): Boolean {
        if (!isValue(node)) return false

        val value = (node as ValueExpression).value
        if (value.type() != Types.NUMBER) return false

        return value.asInt() == valueToCheck
    }
    @JvmStatic
    fun isConstantValue(node: Node): Boolean {
        if (!isValue(node)) return false

        val type = (node as ValueExpression).value.type()
        return ((type == Types.NUMBER) || (type == Types.STRING))
    }
    @JvmStatic
    fun isSameVariables(n1: Node, n2: Node): Boolean {
        if (isVariable(n1) && isVariable(n2)) {
            val v1 = n1 as VariableExpression
            val v2 = n2 as VariableExpression
            return v1.name == v2.name
        }
        return false
    }
    @JvmStatic
    fun operators(): Set<String> {
        val operators: MutableSet<String> = HashSet()
        for (op in BinaryExpression.Operator.entries.toTypedArray()) {
            operators.add(op.toString())
        }
        for (op in UnaryExpression.Operator.entries.toTypedArray()) {
            operators.add(op.toString())
        }
        for (op in ConditionalExpression.Operator.entries.toTypedArray()) {
            operators.add(op.name)
        }
        return operators
    }
}
