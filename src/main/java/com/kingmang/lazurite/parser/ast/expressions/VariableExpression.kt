package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.exceptions.FileInfo
import com.kingmang.lazurite.exceptions.IFileInfoProvider
import com.kingmang.lazurite.exceptions.VariableDoesNotExistsException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ast.Accessible
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.isExists
import com.kingmang.lazurite.runtime.Variables.set
import com.kingmang.lazurite.runtime.values.LzrValue

class VariableExpression(val name: String, override val file: FileInfo?) : InterruptableNode(), Expression, Accessible, IFileInfoProvider {
    override fun eval(): LzrValue {
        super.interruptionCheck()
        return get()
    }

    override fun get(): LzrValue {
        if (!isExists(this.name))
            if (Keyword.isExists(this.name)) {
                throw VariableDoesNotExistsException(this.name, "Do you want to pass function $name? Use '::$name'")
            } else {
                throw VariableDoesNotExistsException(this.name)
            }
        return get(this.name)!!
    }

    override fun set(value: LzrValue): LzrValue {
        set(this.name, value)
        return value
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        this.name
}
