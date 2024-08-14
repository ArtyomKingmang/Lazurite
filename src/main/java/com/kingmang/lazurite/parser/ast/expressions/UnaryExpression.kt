package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException
import com.kingmang.lazurite.parser.ast.Accessible
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.LzrNull
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.fromBoolean
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue
import javafx.scene.shape.VLineTo

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

    private fun overridedOperation(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.CLASS -> {
                value as ClassInstanceValue
                if (value.has("$operation")) {
                    value.callMethod("$operation")
                } else {
                    throw OperationIsNotSupportedException(operation, "for ${value.asString()}")
                }
            }

            else -> {
                throw OperationIsNotSupportedException(operation, value)
            }
        }

    // operator ++

    private fun increment(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.NUMBER -> increment(value as LzrNumber)
            else -> overridedOperation(value)
        }

    private fun increment(value: LzrNumber): LzrValue =
        when (val number = value.raw()) {
            is Double -> of(number.toDouble() + 1)
            is Float -> of(number.toFloat() + 1)
            is Long -> of(number.toLong() + 1)
            else -> LzrNull
        }

    // operator --

    private fun decrement(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.NUMBER -> decrement(value as LzrNumber)
            else -> overridedOperation(value)
        }

    private fun decrement(value: LzrNumber): LzrValue =
        when (val number = value.raw()) {
            is Double -> of(number.toDouble() - 1)
            is Float -> of(number.toFloat() - 1)
            is Long -> of(number.toLong() - 1)
            else -> LzrNull
        }

    // operation -

    private fun negate(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.STRING -> negate(value as LzrString)
            Types.NUMBER -> negate(value as LzrNumber)
            else -> overridedOperation(value)
        }

    private fun negate(value: LzrString): LzrValue {
        val sb = StringBuilder(value.asString())
        return LzrString(sb.reverse().toString())
    }

    private fun negate(value: LzrNumber): LzrValue =
        when (val number = value.raw()) {
            is Double -> of(-number.toDouble())
            is Float -> of(-number.toFloat())
            is Long -> of(-number.toLong())
            else -> of(-value.asInt())
        }

    // operation ~

    private fun complement(value: LzrValue): LzrValue =
        when (value.type()) {
            Types.NUMBER -> complement(value as LzrNumber)
            else -> overridedOperation(value)
        }

    private fun complement(value: LzrNumber): LzrValue {
        val number = value.raw()
        return if (number is Long) {
            of(number.toLong().inv())
        } else {
            of(value.asInt().inv())
        }
    }

    // operation !

    private fun not(value: LzrValue): LzrValue =
        if (value.type() == Types.CLASS) {
            overridedOperation(value)
        } else {
            fromBoolean(value.asInt() == 0)
        }

    // end

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
