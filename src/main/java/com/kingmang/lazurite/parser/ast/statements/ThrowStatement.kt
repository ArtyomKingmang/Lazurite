package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.core.CallStack
import com.kingmang.lazurite.exceptions.FileInfo
import com.kingmang.lazurite.exceptions.LzrTracedException
import com.kingmang.lazurite.exceptions.LzrTracedException.TraceInfo
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

class ThrowStatement(
    private val type: String,
    private val expr: Expression,
    private val file: FileInfo?
) : Statement {
    override fun execute() =
        throw LzrTracedException(this.type, this.expr.eval().toString(), TraceInfo(this.file, CallStack.getCalls()))

    override fun toString(): String =
        "throw ${this.type} ${this.expr}"

    override fun accept(visitor: Visitor) =
        Unit

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        null
}