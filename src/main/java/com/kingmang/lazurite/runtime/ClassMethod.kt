package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.parser.ast.Arguments
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

class ClassMethod(
    arguments: Arguments,
    body: Statement,
    private val lzrClass: ClassInstanceValue
) : UserDefinedFunction(arguments, body) {
    override fun execute(vararg args: LzrValue): LzrValue =
        try {
            Variables.push()
            Variables.define("this", lzrClass)
            super.execute(*args)
        } finally {
            Variables.pop()
        }
}
