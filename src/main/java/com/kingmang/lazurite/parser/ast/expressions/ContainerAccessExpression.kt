package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.FileInfo
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.ast.Accessible
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue

data class ContainerAccessExpression(
    val root: Expression,
    val indices: List<Expression>
) : Expression, Accessible {

    private var rootIsVariable : Boolean = false

    constructor(variable: String, indices: List<Expression>, file: FileInfo) : this(VariableExpression(variable, file), indices)
    {
        rootIsVariable = root is VariableExpression;
    }
    override fun eval(): LzrValue =
        this.get()

    override fun get(): LzrValue {
        val container = this.container
        val lastIndex = this.lastIndex()
        return when (container.type()) {
            Types.ARRAY -> (container as LzrArray)[lastIndex]
            Types.MAP -> (container as LzrMap)[lastIndex]!!
            Types.STRING -> (container as LzrString).access(lastIndex)
            Types.CLASS -> (container as ClassInstanceValue).access(lastIndex)
            else -> throw LzrException("TypeException", "Array or map expected. Got " + Types.typeToString(container.type()))
        }
    }

    override fun set(value: LzrValue): LzrValue {
        val container = this.container
        val lastIndex = lastIndex()
        when (container.type()) {
            Types.ARRAY -> (container as LzrArray)[ lastIndex.asInt()] = value
            Types.MAP -> (container as LzrMap)[lastIndex] = value
            Types.CLASS -> (container as ClassInstanceValue)[lastIndex] = value
            else -> throw LzrException("TypeException", "Array or map expected. Got " + container.type())
        }
        return value
    }

    val container: LzrValue
        get() {
            var container = this.root.eval()
            val last = this.indices.size - 1
            for (i in 0 until last) {
                val index = index(i)
                container = when (container.type()) {
                    Types.ARRAY -> (container as LzrArray)[index.asInt()]
                    Types.MAP -> (container as LzrMap)[index]!!
                    Types.CLASS -> (container as ClassInstanceValue)[index]
                    else -> throw LzrException("TypeException", "Array or map expected")
                }
            }
            return container
        }

    private fun lastIndex(): LzrValue =
        index(this.indices.size - 1)

    private fun index(index: Int): LzrValue =
        this.indices[index].eval()

    fun consumeMap(value: LzrValue): LzrMap {
        if (value.type() != Types.MAP)
            throw LzrException("TypeException", "Map expected")
        return value as LzrMap
    }
    fun rootIsVariable() : Boolean {
        return rootIsVariable;
    }
    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String =
        this.root.toString() + this.indices
}
