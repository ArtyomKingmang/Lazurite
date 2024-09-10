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
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import com.kingmang.lazurite.runtime.values.LzrString

data class ForeachMapStatement(
    val key: String,
    val value: String,
    val container: Expression,
    val body: Statement
) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        val previousVariableValue1 = if (isExists(this.key)) get(this.key) else null
        val previousVariableValue2 = if (isExists(this.value)) get(this.value) else null

        val containerValue = container.eval()
        when (containerValue.type()) {
            Types.STRING -> iterateString(containerValue.asString())
            Types.ARRAY -> iterateArray(containerValue as LzrArray)
            Types.MAP -> iterateMap(containerValue as LzrMap)
            else -> throw LzrException("TypeException", "Cannot iterate " + Types.typeToString(containerValue.type()) + " as key, value pair")
        }
        // Restore variables
        if (previousVariableValue1 != null)
            set(this.key, previousVariableValue1)
        else remove(this.key)
        if (previousVariableValue2 != null)
            set(this.value, previousVariableValue2)
        else remove(this.value)
    }

    private fun iterateString(str: String) {
        for (ch in str.toCharArray()) {
            set(this.key, LzrString(ch.toString()))
            set(this.value, of(ch.code))
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
        for ((index, v) in containerValue.withIndex()) {
            set(this.key, v)
            set(this.value, of(index))
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
        for ((key1, value1) in containerValue) {
            set(this.key, key1)
            set(this.value, value1)
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
        String.format("for %s, %s : %s %s", key, value, container, body)
}
