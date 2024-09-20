package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

class DoWhileStatement(val condition: Expression, val statement: Statement) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        do {
            try {
                statement.execute()
            } catch (bs: BreakStatement) {
                break
            } catch (cs: ContinueStatement) {
                continue
            }
        } while (condition.eval().asInt() != 0)
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R? {
        return visitor.visit(this, t)
    }

    override fun toString(): String {
        return "do $statement while $condition"
    }
}
