package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.checkRange
import com.kingmang.lazurite.parser.ast.Arguments
import com.kingmang.lazurite.parser.ast.statements.ReturnStatement
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue

open class UserDefinedFunction(
    val arguments: Arguments,
    val body: Statement
) : Function {
    fun getArgsCount(): Int =
        arguments.size()

    override fun execute(vararg args: LzrValue): LzrValue {
        val requiredArgsCount = arguments.requiredCount
        val totalArgsCount = getArgsCount()
        args.checkRange(requiredArgsCount, totalArgsCount)

        return try {
            Variables.push()
            for (i in 0 until requiredArgsCount)
                Variables.define(arguments.get(i).name, args[i])
            // Optional args if exists
            for (i in requiredArgsCount until totalArgsCount) {
                val arg = arguments.get(i)
                if (arg.valueExpr == null)
                    continue
                Variables.define(arg.name, arg.valueExpr.eval())
            }
            body.execute()
            LzrNumber.ZERO
        } catch (rt: ReturnStatement) {
            rt.result!!
        } finally {
            Variables.pop()
        }
    }

    override fun toString(): String {
        if (body is ReturnStatement) {
            return "func$arguments = ${body.expression}"
        }
        return "func$arguments $body"
    }
}
