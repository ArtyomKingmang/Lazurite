package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

data class WhileStatement(
    val condition: Expression,
    val statement: Statement
) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        while (this.condition.eval().asInt() != 0) {
            try {
                this.statement.execute()
            } catch (bs: BreakStatement) {
                break
            } catch (cs: ContinueStatement) {
                // continue;
            }
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        "while ${this.condition} ${this.statement}"
}
