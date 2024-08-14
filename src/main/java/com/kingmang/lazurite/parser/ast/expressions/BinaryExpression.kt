package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*
import com.kingmang.lazurite.runtime.values.LzrArray.Companion.merge
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import kotlin.math.max

data class BinaryExpression(
    val operation: Operator,
    val expr1: Expression,
    val expr2: Expression
) : Expression {
    override fun eval(): LzrValue {
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
            Operator.ADD -> add(value1, value2)
            Operator.SUBTRACT -> subtract(value1, value2)
            Operator.MULTIPLY -> multiply(value1, value2)
            Operator.DIVIDE -> divide(value1, value2)
            Operator.REMAINDER -> remainder(value1, value2)

            Operator.PUSH -> push(value1, value2)

            Operator.AND -> and(value1, value2)
            Operator.OR -> or(value1, value2)
            Operator.XOR -> xor(value1, value2)

            Operator.LSHIFT -> lshift(value1, value2)
            Operator.RSHIFT -> rshift(value1, value2)
            Operator.URSHIFT -> urshift(value1, value2)
            else -> throw OperationIsNotSupportedException(operation)
        }

    private fun overridedOperation(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.CLASS -> {
                when (value2.type()) {
                    Types.CLASS -> overridedOperation(value1 as ClassInstanceValue, value2 as ClassInstanceValue)
                    else -> overridedOperation(value1 as ClassInstanceValue, value2)
                }
            }

            else -> {
                when (value2.type()) {
                    Types.CLASS -> overridedOperation(value1, value2 as ClassInstanceValue)
                    else -> throw OperationIsNotSupportedException(operation, value1.type(), value2.type())
                }
            }
        }

    private fun overridedOperation(value1: ClassInstanceValue, value2: ClassInstanceValue): LzrValue =
        if (value1.has(operation.toString())) {
            value1.callMethod(operation.toString(), value2)
        } else if (value2.has("r$operation")) {
            value2.callMethod("r$operation", value1)
        } else {
            throw OperationIsNotSupportedException(operation.toString(), value1.asString(), value2.asString())
        }

    private fun overridedOperation(value1: ClassInstanceValue, value2: LzrValue): LzrValue =
        if (value1.has(operation.toString())) {
            value1.callMethod(operation.toString(), value2)
        } else {
            throw OperationIsNotSupportedException(operation.toString(), value1.asString(), value2.type())
        }

    private fun overridedOperation(value1: LzrValue, value2: ClassInstanceValue): LzrValue =
        if (value2.has("r$operation")) {
            value2.callMethod("r$operation", value1)
        } else {
            throw OperationIsNotSupportedException(operation.toString(), value1.type(), value2.asString())
        }

    // operator +

    private fun add(value1: LzrValue, value2: LzrValue): LzrValue {
        if ((value1.type() == Types.CLASS) || (value2.type() == Types.CLASS)) {
            return overridedOperation(value1, value2)
        }
        return when (value1.type()) {
            Types.NUMBER -> add(value1 as LzrNumber, value2)
            Types.ARRAY -> add(value1 as LzrArray, value2)
            Types.MAP -> {
                when (value2.type()) {
                    Types.MAP -> add(value1 as LzrMap, value2 as LzrMap)
                    Types.CLASS -> throw OperationIsNotSupportedException(
                        operation.toString(),
                        value1.type(),
                        value2.asString()
                    )
                    else -> throw OperationIsNotSupportedException(operation.toString(), value1.type(), value2.type())
                }
            }

            else -> LzrString(value1.asString() + value2.asString())
        }
    }

    private fun add(value1: LzrNumber, value2: LzrValue): LzrValue =
        if (value2.type() == Types.NUMBER) {
            // number1 + number2
            val number1 = value1.raw()
            val number2 = (value2 as LzrNumber).raw()
            when {
                number1 is Double || number2 is Double -> of(number1.toDouble() + number2.toDouble())
                number1 is Float || number2 is Float -> of(number1.toFloat() + number2.toFloat())
                number1 is Long || number2 is Long -> of(number1.toLong() + number2.toLong())
                else -> of(number1.toInt() + number2.toInt())
            }
        } else {
            // number1 + other
            when (val number1 = value1.raw()) {
                is Double -> of(number1.toDouble() + value2.asNumber())
                is Float -> of(number1.toFloat() + value2.asNumber())
                is Long -> of(number1.toLong() + value2.asInt())
                else -> of(number1.toInt() + value2.asInt())
            }
        }

    private fun add(value1: LzrArray, value2: LzrValue) =
        value1.add(value2)

    private fun add(value1: LzrMap, value2: LzrMap) =
        value1.merge(value2)

    // operator -

    private fun subtract(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> subtract(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun subtract(value1: LzrNumber, value2: LzrValue): LzrValue =
        if (value2.type() == Types.NUMBER) {
            // number1 - number2
            val number1 = value1.raw()
            val number2 = (value2 as LzrNumber).raw()
            when {
                number1 is Double || number2 is Double -> of(number1.toDouble() - number2.toDouble())
                number1 is Float || number2 is Float -> of(number1.toFloat() - number2.toFloat())
                number1 is Long || number2 is Long -> of(number1.toLong() - number2.toLong())
                else -> of(number1.toInt() - number2.toInt())
            }
        } else {
            // number1 - other
            when (val number1 = value1.raw()) {
                is Double -> of(number1.toDouble() - value2.asNumber())
                is Float -> of(number1.toFloat() - value2.asNumber())
                is Long -> of(number1.toLong() - value2.asInt())
                else -> of(number1.toInt() - value2.asInt())
            }
        }

    // operator *

    private fun multiply(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> multiply(value1 as LzrNumber, value2)
            Types.STRING -> multiply(value1 as LzrString, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun multiply(value1: LzrNumber, value2: LzrValue): LzrValue =
        if (value2.type() == Types.NUMBER) {
            // number1 * number2
            val number1 = value1.raw()
            val number2 = (value2 as LzrNumber).raw()
            when {
                number1 is Double || number2 is Double -> of(number1.toDouble() * number2.toDouble())
                number1 is Float || number2 is Float -> of(number1.toFloat() * number2.toFloat())
                number1 is Long || number2 is Long -> of(number1.toLong() * number2.toLong())
                else -> of(number1.toInt() * number2.toInt())
            }
        } else {
            // number1 * other
            when (val number1 = value1.raw()) {
                is Double -> of(number1.toDouble() * value2.asNumber())
                is Float -> of(number1.toFloat() * value2.asNumber())
                is Long -> of(number1.toLong() * value2.asInt())
                else -> of(number1.toInt() * value2.asInt())
            }
        }

    private fun multiply(value1: LzrString, value2: LzrValue): LzrValue {
        val string1 = value1.asString()
        val iterations = value2.asInt()
        return LzrString(string1.repeat(max(0.0, iterations.toDouble()).toInt()))
    }

    // operator /

    private fun divide(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> divide(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun divide(value1: LzrNumber, value2: LzrValue): LzrValue =
        if (value2.type() == Types.NUMBER) {
            // number1 / number2
            val number1 = value1.raw()
            val number2 = (value2 as LzrNumber).raw()
            when {
                number1 is Double || number2 is Double -> of(number1.toDouble() / number2.toDouble())
                number1 is Float || number2 is Float -> of(number1.toFloat() / number2.toFloat())
                number1 is Long || number2 is Long -> of(number1.toLong() / number2.toLong())
                else -> of(number1.toInt() / number2.toInt())
            }
        } else {
            // number1 / other
            when (val number1 = value1.raw()) {
                is Double -> of(number1.toDouble() / value2.asNumber())
                is Float -> of(number1.toFloat() / value2.asNumber())
                is Long -> of(number1.toLong() / value2.asInt())
                else -> of(number1.toInt() / value2.asInt())
            }
        }

    // operator %

    private fun remainder(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> remainder(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun remainder(value1: LzrNumber, value2: LzrValue): LzrValue =
        if (value2.type() == Types.NUMBER) {
            // number1 % number2
            val number1 = value1.raw()
            val number2 = (value2 as LzrNumber).raw()
            when {
                number1 is Double || number2 is Double -> of(number1.toDouble() % number2.toDouble())
                number1 is Float || number2 is Float -> of(number1.toFloat() % number2.toFloat())
                number1 is Long || number2 is Long -> of(number1.toLong() % number2.toLong())
                else -> of(number1.toInt() % number2.toInt())
            }
        } else {
            // number1 % other
            when (val number1 = value1.raw()) {
                is Double -> of(number1.toDouble() % value2.asNumber())
                is Float -> of(number1.toFloat() % value2.asNumber())
                is Long -> of(number1.toLong() % value2.asInt())
                else -> of(number1.toInt() % value2.asInt())
            }
        }

    // operator ::

    private fun push(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.ARRAY -> push(value1 as LzrArray, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun push(value1: LzrArray, value2: LzrValue): LzrArray =
        LzrArray.add(value1, value2)

    // operator &

    private fun and(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> and(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun and(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 & number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() and number2.toLong())
            return of(number1.toInt() and number2.toInt())
        }
        // number1 & other
        if (number1 is Long)
            return of(number1.toLong() and value2.asInt().toLong())
        return of(number1.toInt() and value2.asInt())
    }

    // operation |

    private fun or(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> or(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun or(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 | number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() or number2.toLong())
            return of(number1.toInt() or number2.toInt())
        }
        // number1 | other
        if (number1 is Long)
            return of(number1.toLong() or value2.asInt().toLong())
        return of(number1.toInt() or value2.asInt())
    }

    // operation ^

    private fun xor(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> xor(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun xor(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 ^ number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() xor number2.toLong())
            return of(number1.toInt() xor number2.toInt())
        }
        // number1 ^ other
        if (number1 is Long)
            return of(number1.toLong() xor value2.asInt().toLong())
        return of(number1.toInt() xor value2.asInt())
    }

    // operation <<

    private fun lshift(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> lshift(value1 as LzrNumber, value2)
            Types.ARRAY -> lshift(value1 as LzrArray, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun lshift(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 << number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() shl number2.toLong().toInt())
            return of(number1.toInt() shl number2.toInt())
        }
        // number1 << other
        if (number1 is Long)
            return of(number1.toLong() shl value2.asInt())
        return of(number1.toInt() shl value2.asInt())
    }

    private fun lshift(value1: LzrArray, value2: LzrValue): LzrValue {
        if (value2.type() != Types.ARRAY)
            throw LzrException("TypeException", "Cannot merge non array value to array")
        return merge(value1, (value2 as LzrArray))
    }

    // operation >>

    private fun rshift(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> rshift(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun rshift(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 >> number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() shr number2.toLong().toInt())
            return of(number1.toInt() shr number2.toInt())
        }
        // number1 >> other
        if (number1 is Long)
            return of(number1.toLong() shr value2.asInt())
        return of(number1.toInt() shr value2.asInt())
    }

    // operation >>>

    private fun urshift(value1: LzrValue, value2: LzrValue): LzrValue =
        when (value1.type()) {
            Types.NUMBER -> urshift(value1 as LzrNumber, value2)
            else -> overridedOperation(value1, value2)
        }

    private fun urshift(value1: LzrNumber, value2: LzrValue): LzrValue {
        val number1 = value1.raw()
        if (value2.type() == Types.NUMBER) {
            // number1 >>> number2
            val number2 = (value2 as LzrNumber).raw()
            if (number1 is Long || number2 is Long)
                return of(number1.toLong() ushr number2.toLong().toInt())
            return of(number1.toInt() ushr number2.toInt())
        }
        // number1 >>> other
        if (number1 is Long)
            return of(number1.toLong() ushr value2.asInt())
        return of(number1.toInt() ushr value2.asInt())
    }

    // end

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        String.format("%s %s %s", expr1, operation, expr2)


    enum class Operator(val text : String) {
        PUSH("::"),
        AND("&"),
        OR("|"),
        XOR("^"),
        LSHIFT("<<"),
        RSHIFT(">>"),
        URSHIFT(">>>"),
        AT("@"),
        CARETCARET("^^"),
        RANGE(".."),
        POWER("**"),
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        REMAINDER("%"),
        ELVIS("?:");

        override fun toString(): String =
            this.text
    }
}
