package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString

data class TryCatchStatement(
    val tryStatement: Statement,
    val catchStatement: Statement
) : Statement {

    override fun execute() {
        try {
            this.tryStatement.execute()
        } catch (ex: LzrException) {
            val exInfo = LzrMap(2)
            exInfo["type"] = LzrString(ex.type)
            exInfo["text"] = LzrString(ex.message!!)
            define("exception", exInfo)
            this.catchStatement.execute()
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        "try " + this.tryStatement + "catch " + this.catchStatement
}