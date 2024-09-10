package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.isExists
import com.kingmang.lazurite.runtime.Variables.remove
import com.kingmang.lazurite.runtime.Variables.set
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString

data class ForeachArrayStatement(
    val variable: String,
    val container: Expression,
    val body: Statement
) : InterruptableNode(), Statement {

    override fun execute() {
        super.interruptionCheck()
        val previousVariableValue = if (isExists(this.variable)) get(this.variable) else null

        val containerValue = container.eval()
        when (containerValue.type()) {
            Types.STRING -> iterateString(containerValue.asString())
            Types.ARRAY -> iterateArray(containerValue as LzrArray)
            Types.MAP -> iterateMap(containerValue as LzrMap)
            else -> throw LzrException("TypeExeption", "Cannot iterate " + Types.typeToString(containerValue.type()))
        }
        // Restore variables
        if (previousVariableValue != null) {
            set(this.variable, previousVariableValue)
        } else {
            remove(this.variable)
        }
    }

    private fun iterateString(str: String) {
        for (ch in str.toCharArray()) {
            set(this.variable, LzrString(ch.toString()))
            try {
                this.body.execute()
            } catch (bs: BreakStatement) {
                break
            } catch (cs: ContinueStatement) {
                // continue;
            }
        }
    }

    private fun iterateArray(containerValue: LzrArray) {
        for (value in containerValue) {
            set(this.variable, value)
            try {
                this.body.execute()
            } catch (bs: BreakStatement) {
                break
            } catch (cs: ContinueStatement) {
                // continue;
            }
        }
    }

    private fun iterateMap(containerValue: LzrMap) {
        for ((key, value) in containerValue) {
            set(this.variable, LzrArray(arrayOf(key, value)))
            try {
                this.body.execute()
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
        String.format("for %s : %s %s", this.variable, this.container, this.body)
}
