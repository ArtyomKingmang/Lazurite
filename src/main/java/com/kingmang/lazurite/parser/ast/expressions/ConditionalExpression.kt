package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue

data class ConditionalExpression(
    val operation: Operator,
    val expr1: Expression,
    val expr2: Expression
) : Expression {
    override fun eval(): LzrValue {
        if (operation == Operator.NULL_COALESCE) {
            return nullCoalesce()
        }

        val value1 = expr1.eval()
        val value2 = expr2.eval()

        try {
            return eval(value1, value2)
        } catch (ex: OperationIsNotSupportedException) {
            if (Keyword.isExists(operation.toString()))
                return Keyword.get(operation.toString()).execute(value1, value2)
            throw ex
        }
    }

    private fun eval(value1: LzrValue, value2: LzrValue): LzrValue =
        when (operation) {
            Operator.EQUALS -> equals(value1, value2)
            Operator.NOT_EQUALS -> notEquals(value1, value2)

            Operator.LT -> lt(value1, value2)
            Operator.LTEQ -> lteq(value1, value2)
            Operator.GT -> gt(value1, value2)
            Operator.GTEQ -> gteq(value1, value2)

            Operator.AND -> and(value1, value2)
            Operator.OR -> or(value1, value2)

            else -> throw OperationIsNotSupportedException(operation, value1.type(), value2.type())
        }

    // Если у классов реализованы операторы, то вернуть результат. Иначе null
    private fun overridedOperation(value1: LzrValue, value2: LzrValue): LzrValue? {
        if (value1.type() == Types.CLASS) {
            value1 as ClassInstanceValue
            if (value1.has("$operation")) {
                return value1.callMethod("$operation", value2)
            }
        }
        if (value2.type() == Types.CLASS) {
            value2 as ClassInstanceValue
            if (value2.has("r$operation")) {
                return value2.callMethod("r$operation", value1)
            }
        }

        return null
    }

    private fun equals(value1: LzrValue, value2: LzrValue): LzrValue =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 == value2)

    private fun notEquals(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 != value2)

    private fun lt(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 < value2)

    private fun lteq(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 <= value2)

    private fun gt(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 > value2)

    private fun gteq(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean(value1 >= value2)

    private fun and(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean((value1.asInt() != 0) && (value1.asInt() != 0))

    private fun or(value1: LzrValue, value2: LzrValue) =
        overridedOperation(value1, value2)
            ?: LzrNumber.fromBoolean((value1.asInt() != 0) || (value2.asInt() != 0))

    private fun nullCoalesce(): LzrValue =
        try {
            expr1.eval()
        } catch (npe: NullPointerException) {
            expr2.eval()
        }

    // end

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