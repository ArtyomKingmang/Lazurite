package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.console.Console
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

class BlockStatement : InterruptableNode(), Statement {
    val statements: MutableList<Statement> = ArrayList()

    fun add(statement: Statement) {
        this.statements.add(statement)
    }

    override fun execute() {
        super.interruptionCheck()
        this.statements.forEach(Statement::execute)
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        StringBuilder().run {
            statements.forEach { this.append(it.toString()).append(Console.newline()) }
            toString()
        }
}
