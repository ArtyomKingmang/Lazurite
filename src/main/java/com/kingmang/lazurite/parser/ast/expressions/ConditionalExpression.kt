package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.fromBoolean
import com.kingmang.lazurite.runtime.values.LzrValue

data class ConditionalExpression(
    val operation: Operator,
    val expr1: Expression,
    val expr2: Expression
) : Expression {
    override fun eval(): LzrValue =
        when (operation) {
            Operator.AND -> fromBoolean((expr1AsInt() != 0) && (expr2AsInt() != 0))
            Operator.OR -> fromBoolean((expr1AsInt() != 0) || (expr2AsInt() != 0))
            Operator.NULL_COALESCE -> nullCoalesce()
            else -> fromBoolean(evalAndCompare())
        }

    private fun evalAndCompare(): Boolean {
        val value1 = expr1.eval()
        val value2 = expr2.eval()

        val number1: Double
        val number2: Double
        if (value1.type() == Types.NUMBER) {
            number1 = value1.asNumber()
            number2 = value2.asNumber()
        } else {
            number1 = value1.compareTo(value2).toDouble()
            number2 = 0.0
        }

        return when (operation) {
            Operator.EQUALS -> number1 == number2
            Operator.NOT_EQUALS -> number1 != number2
            Operator.LT -> number1 < number2
            Operator.LTEQ -> number1 <= number2
            Operator.GT -> number1 > number2
            Operator.GTEQ -> number1 >= number2
            else -> throw OperationIsNotSupportedException(operation)
        }
    }

    private fun nullCoalesce(): LzrValue =
        try {
            expr1.eval()
        } catch (npe: NullPointerException) {
            expr2.eval()
        }

    private fun expr1AsInt(): Int =
        expr1.eval().asInt()

    private fun expr2AsInt(): Int =
        expr2.eval().asInt()

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        String.format("%s %s %s", expr1, operation.text, expr2)

    enum class Operator(val text: String) {
        EQUALS("=="),
        NOT_EQUALS("!="),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||"),

        NULL_COALESCE("??");

        override fun toString(): String =
            this.text
    }
}