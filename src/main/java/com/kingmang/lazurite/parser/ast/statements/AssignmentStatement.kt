package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.set
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import com.kingmang.lazurite.runtime.values.LzrString

data class AssignmentStatement(
    val variable: String,
    val expression: Expression,
    val mode: Int
) : Statement {
    constructor(variable: String, expression: Expression) : this(variable, expression, 0)

    override fun execute() {
        if (this.mode == 0) {
            val result = expression.eval()
            set(this.variable, result)
        } else {
            try {
                // Is number
                get(this.variable)!!.asString().toInt()
                // =========
                when (this.mode) {
                    1 -> {
                        val result = this.expression.eval()
                        set(this.variable, of(get(this.variable)!!.asInt() + result.asInt()))
                    }

                    2 -> {
                        val result = this.expression.eval()
                        set(this.variable, of(get(this.variable)!!.asInt() - result.asInt()))
                    }

                    3 -> {
                        val result = this.expression.eval()
                        set(this.variable, of(get(this.variable)!!.asInt() * result.asInt()))
                    }

                    4 -> {
                        val result = this.expression.eval()
                        set(this.variable, of(get(this.variable)!!.asInt() / result.asInt()))
                    }
                }
            } catch (ex: Exception) {
                if (this.mode == 1) {
                    val result = this.expression.eval()
                    set(this.variable, LzrString(get(this.variable).toString() + result.toString()))
                } else throw LzrException("TypeError", "non-applicable operation to string")
            }
        }
    }


    override fun toString(): String =
        String.format("%s = %s", this.variable, this.expression)

    override fun accept(visitor: Visitor) =
        Unit

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        null
}