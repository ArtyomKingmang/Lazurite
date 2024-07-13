package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.parser.AST.Arguments
import com.kingmang.lazurite.parser.AST.Statements.Statement
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

class ClassMethod(
    arguments: Arguments,
    body: Statement,
    private val thisMap: LzrMap
) : UserDefinedFunction(arguments, body) {

    override fun execute(vararg args: LzrValue): LzrValue {
        Variables.push()
        Variables.define("this", thisMap)

        try {
            return super.execute(*args)
        } finally {
            Variables.pop()
        }
    }
}
