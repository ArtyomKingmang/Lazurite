package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.ast.Accessible
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.fromBoolean
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue

data class UnaryExpression(
    val operation: Operator,
    val expr1: Expression
) : Expression, Statement {
    override fun execute() {
        eval()
    }

    override fun eval(): LzrValue {
        val value = this.expr1.eval()
        return when (this.operation) {
            Operator.INCREMENT_PREFIX -> {
                if (this.expr1 is Accessible)
                    this.expr1.set(increment(value))
                else increment(value)
            }

            Operator.DECREMENT_PREFIX -> {
                if (this.expr1 is Accessible)
                    this.expr1.set(decrement(value))
                else decrement(value)
            }

            Operator.INCREMENT_POSTFIX -> {
                if (this.expr1 is Accessible) {
                    this.expr1.set(increment(value))
                    value
                } else increment(value)
            }

            Operator.DECREMENT_POSTFIX -> {
                if (this.expr1 is Accessible) {
                    this.expr1.set(decrement(value))
                    value
                } else decrement(value)
            }

            Operator.NEGATE -> negate(value)
            Operator.COMPLEMENT -> complement(value)
            Operator.NOT -> not(value)
        }
    }

    private fun increment(value: LzrValue): LzrValue {
        if (value.type() == Types.NUMBER) {
            when (val number = (value as LzrNumber).raw()) {
                is Double -> return of(number.toDouble() + 1)
                is Float -> return of(number.toFloat() + 1)
                is Long -> return of(number.toLong() + 1)
            }
        }
        return of(value.asInt() + 1)
    }

    private fun decrement(value: LzrValue): LzrValue {
        if (value.type() == Types.NUMBER) {
            when (val number = (value as LzrNumber).raw()) {
                is Double -> return of(number.toDouble() - 1)
                is Float -> return of(number.toFloat() - 1)
                is Long -> return of(number.toLong() - 1)
            }
        }
        return of(value.asInt() - 1)
    }

    private fun negate(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.STRING -> {
                val sb = StringBuilder(value.asString())
                LzrString(sb.reverse().toString())
            }

            Types.NUMBER -> {
                when (val number = (value as LzrNumber).raw()) {
                    is Double -> of(-number.toDouble())
                    is Float -> of(-number.toFloat())
                    is Long -> of(-number.toLong())
                    else -> of(-value.asInt())
                }
            }

            else -> of(-value.asInt())
        }

    private fun complement(value: LzrValue): LzrValue {
        if (value.type() == Types.NUMBER) {
            val number = (value as LzrNumber).raw()
            if (number is Long) {
                return of(number.toLong().inv())
            }
        }
        return of(value.asInt().inv())
    }

    private fun not(value: LzrValue): LzrValue =
        fromBoolean(value.asInt() == 0)

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        when (this.operation) {
            Operator.INCREMENT_POSTFIX, Operator.DECREMENT_POSTFIX -> String.format("%s %s", expr1, this.operation)
            else -> String.format("%s %s", this.operation, expr1)
        }


    enum class Operator(val text: String) {
        INCREMENT_PREFIX("++"),
        DECREMENT_PREFIX("--"),
        INCREMENT_POSTFIX("++"),
        DECREMENT_POSTFIX("--"),
        NEGATE("-"),

        // Boolean
        NOT("!"),

        // Bitwise
        COMPLEMENT("~");

        override fun toString(): String =
            this.text
    }
}
