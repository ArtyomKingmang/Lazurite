package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.statements.ReturnStatement
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.isExists
import com.kingmang.lazurite.runtime.Variables.remove
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue

data class MatchExpression(val expression: Expression, val patterns: List<Pattern>) : InterruptableNode(), Expression, Statement {
    override fun execute() {
        eval()
    }

    override fun eval(): LzrValue {
        super.interruptionCheck()
        val value = expression.eval()
        for (p in patterns) {
            if (p is ConstantPattern && match(value, p.constant) && optMatches(p))
                return evalResult(p.result)
            if (p is VariablePattern) {
                if (p.variable == "_")
                    return evalResult(p.result)
                if (isExists(p.variable)) {
                    if (match(value, get(p.variable)) && optMatches(p)) {
                        return evalResult(p.result)
                    }
                } else {
                    define(p.variable, value)
                    if (optMatches(p)) {
                        return evalResult(p.result).also {
                            remove(p.variable)
                        }
                    }
                    remove(p.variable)
                }
            }
            if ((value.type() == Types.ARRAY) && (p is ListPattern) && matchListPattern(value as LzrArray, p)) {
                return evalResult(p.result).also {
                    p.parts.forEach(::remove)
                }
            }
            if ((value.type() == Types.ARRAY) && (p is TuplePattern) && matchTuplePattern(value as LzrArray, p) && optMatches(p)) {
                return evalResult(p.result)
            }
        }
        throw LzrException("PatternMatchingException", "No pattern were matched")
    }

    private fun matchTuplePattern(array: LzrArray, p: TuplePattern): Boolean {
        if (p.values.size != array.size())
            return false
        val size = array.size()
        for (i in 0 until size) {
            val expr = p.values[i]
            if ((expr !== TuplePattern.ANY) && (expr.eval().compareTo(array[i]) != 0)) {
                return false
            }
        }
        return true
    }

    private fun matchListPattern(array: LzrArray, p: ListPattern): Boolean {
        val parts: List<String> = p.parts
        val partsSize = parts.size
        val arraySize = array.size()
        return when (partsSize) {
            0 -> {
                (arraySize == 0) && optMatches(p)
            }

            1 -> {
                val variable = parts[0]
                define(variable, array)
                if (optMatches(p)) {
                    true
                } else {
                    remove(variable)
                    false
                }
            }

            else -> {
                if (partsSize == arraySize)
                    matchListPatternEqualsSize(p, parts, partsSize, array)
                else if (partsSize < arraySize)
                    matchListPatternWithTail(p, parts, partsSize, array, arraySize)
                else false
            }
        }
    }

    private fun matchListPatternEqualsSize(p: ListPattern, parts: List<String>, partsSize: Int, array: LzrArray): Boolean {
        for (i in 0 until partsSize)
            define(parts[i], array[i])
        if (optMatches(p))
            return true
        parts.forEach(::remove)
        return false
    }

    private fun matchListPatternWithTail(p: ListPattern, parts: List<String>, partsSize: Int, array: LzrArray, arraySize: Int): Boolean {
        val lastPart = partsSize - 1
        for (i in 0 until lastPart)
            define(parts[i], array[i])
        val tail = LzrArray(arraySize - partsSize + 1)
        for (i in lastPart until arraySize)
            tail[i - lastPart] = array[i]
        define(parts[lastPart], tail)
        if (optMatches(p))
            return true
        parts.forEach(::remove)
        return false
    }

    private fun match(value: LzrValue, constant: LzrValue?): Boolean {
        if (value.type() != constant!!.type())
            return false
        return value == constant
    }

    private fun optMatches(pattern: Pattern): Boolean {
        if (pattern.optCondition == null)
            return true
        return pattern.optCondition!!.eval() !== LzrNumber.ZERO
    }

    private fun evalResult(s: Statement?): LzrValue =
        try {
            s!!.execute()
            LzrNumber.ZERO
        } catch (ret: ReturnStatement) {
            ret.result!!
        }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("match ").append(expression).append(" {")
        patterns.forEach { sb.append("\n  case ").append(it) }
        sb.append("\n}")
        return sb.toString()
    }

    abstract class Pattern {
        var result: Statement? = null
        var optCondition: Expression? = null

        override fun toString(): String {
            val sb = StringBuilder()
            if (this.optCondition != null)
                sb.append(" if ").append(this.optCondition)
            sb.append(": ").append(this.result)
            return sb.toString()
        }
    }

    class ConstantPattern(var constant: LzrValue) : Pattern() {
        override fun toString(): String =
            this.constant.toString() + super.toString()
    }

    class VariablePattern(var variable: String) : Pattern() {
        override fun toString(): String =
            this.variable + super.toString()
    }

    class ListPattern internal constructor(var parts: MutableList<String>) : Pattern() {
        constructor() : this(ArrayList<String>())

        fun add(part: String) {
            parts.add(part)
        }

        override fun toString(): String {
            val it: Iterator<String> = parts.iterator()
            if (it.hasNext()) {
                val sb = StringBuilder()
                sb.append("[").append(it.next())
                while (it.hasNext()) {
                    sb.append(" :: ").append(it.next())
                }
                sb.append("]").append(super.toString())
                return sb.toString()
            }
            return "[]" + super.toString()
        }
    }

    class TuplePattern(var values: MutableList<Expression> = ArrayList()) : Pattern() {
        fun addAny() {
            this.values.add(ANY)
        }

        fun add(value: Expression) {
            this.values.add(value)
        }

        override fun toString(): String {
            val it: Iterator<Expression> = this.values.iterator()
            if (it.hasNext()) {
                val sb = StringBuilder()
                sb.append('(').append(it.next())
                while (it.hasNext()) {
                    sb.append(", ").append(it.next())
                }
                sb.append(')').append(super.toString())
                return sb.toString()
            }
            return "()" + super.toString()
        }

        companion object {
            val ANY: Expression = object : Expression {
                override fun eval(): LzrValue =
                    LzrNumber.ONE

                override fun accept(visitor: Visitor) =
                    Unit

                override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
                    null

                override fun toString(): String =
                    "_" + super.toString()
            }
        }
    }
}
