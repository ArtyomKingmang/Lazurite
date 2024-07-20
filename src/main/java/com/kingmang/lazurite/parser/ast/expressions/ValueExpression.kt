package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue

data class ValueExpression(val value: LzrValue) : InterruptableNode(), Expression {
    constructor(value: Number?) : this(of(value!!))
    constructor(value: String?) : this(LzrString(value!!))
    constructor(value: Function?) : this(LzrFunction(value!!))

    override fun eval(): LzrValue {
        super.interruptionCheck()
        return this.value
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        if (value.type() == Types.STRING)
            "\"" + value.asString() + "\""
        else value.toString()
}
