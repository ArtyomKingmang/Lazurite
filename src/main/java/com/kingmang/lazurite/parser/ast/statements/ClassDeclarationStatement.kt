package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.core.ClassDeclarations
import com.kingmang.lazurite.parser.ast.expressions.AssignmentExpression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

data class ClassDeclarationStatement(val name: String) : Statement {
    val methods: MutableList<FunctionDefineStatement> = ArrayList()
    val fields: MutableList<AssignmentExpression> = ArrayList()

    fun addField(expr: AssignmentExpression) {
        this.fields.add(expr)
    }

    fun addMethod(statement: FunctionDefineStatement) {
        this.methods.add(statement)
    }

    override fun execute() {
        ClassDeclarations.set(this.name, this)
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        String.format("class %s {\n  %s  %s}", name, fields, methods)
}
