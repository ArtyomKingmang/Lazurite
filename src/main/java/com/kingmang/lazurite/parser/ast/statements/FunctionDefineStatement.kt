package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ast.Arguments
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.UserDefinedFunction

data class FunctionDefineStatement(
    val name: String,
    val arguments: Arguments,
    val body: Statement
) : Statement {
    override fun execute() {
        Keyword.put(this.name, UserDefinedFunction(this.arguments, this.body))
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        if (body is ReturnStatement)
            String.format("func %s%s = %s", this.name, this.arguments, this.body.expression)
        else String.format("func %s%s %s", this.name, this.arguments, this.body)
}
