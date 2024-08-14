package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.CallStack
import com.kingmang.lazurite.core.ClassDeclarations
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.IFileInfoProvider
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.exceptions.LzrTracedException
import com.kingmang.lazurite.exceptions.LzrTracedException.TraceInfo
import com.kingmang.lazurite.exceptions.VariableDoesNotExistsException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.isExists
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrValue

data class FunctionalExpression(val functionExpr: Expression, val arguments: MutableList<Expression>) : InterruptableNode(), Expression, Statement {
    constructor(functionExpr: Expression) : this(functionExpr, ArrayList())

    fun addArgument(arg: Expression) {
        this.arguments.add(arg)
    }

    override fun execute() {
        eval()
    }

    override fun eval(): LzrValue {
        super.interruptionCheck()
        val values = this.arguments.map { it.eval() }.toTypedArray()
        val f = consumeFunction(this.functionExpr)
        if (this.functionExpr is IFileInfoProvider)
            CallStack.enter(this.functionExpr.toString(), f, this.functionExpr.file)
        else CallStack.enter(this.functionExpr.toString(), f, null)
        try {
            val result = f.execute(*values)
            CallStack.exit()
            return result
        } catch (ex: LzrTracedException) {
            throw ex
        } catch (ex: LzrException) {
            val info =
                if (this.functionExpr is IFileInfoProvider)
                    TraceInfo(this.functionExpr.file, CallStack.getCalls())
                else TraceInfo(null, CallStack.getCalls())
            CallStack.exit()
            throw LzrTracedException(ex.type, ex.message!!, info)
        }
    }

    private fun consumeFunction(expr: Expression): Function {
        try {
            val value = expr.eval()
            if (value.type() == Types.FUNCTION)
                return (value as LzrFunction).value
            return getFunction(value.asString())
        } catch (ex: VariableDoesNotExistsException) {
            return getFunction(ex.variable)
        }
    }

    private fun getFunction(key: String): Function {
        if (Keyword.isExists(key))
            return Keyword.get(key)
        if (isExists(key)) {
            val variable = get(key)
            if (variable!!.type() == Types.FUNCTION) {
                return (variable as LzrFunction?)!!.value
            }
        }

        if (ClassDeclarations[key] != null) {
            throw LzrException("UnknownFunctionException", "Unknown function $key. You trying to create an instance of class $key? Use the keyword 'new'")
        } else {
            throw LzrException("UnknownFunctionException", "Unknown function $key")
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R =
        visitor.visit(this, t)

    override fun toString(): String {
        val sb = StringBuilder()
        if (functionExpr is ValueExpression && functionExpr.value.type() == Types.STRING)
            sb.append(functionExpr.value.asString()).append('(')
        else sb.append(functionExpr).append('(')
        val it: Iterator<Expression> = arguments.iterator()
        if (it.hasNext()) {
            sb.append(it.next())
            while (it.hasNext()) {
                sb.append(", ").append(it.next())
            }
        }
        sb.append(')')
        return sb.toString()
    }
}
