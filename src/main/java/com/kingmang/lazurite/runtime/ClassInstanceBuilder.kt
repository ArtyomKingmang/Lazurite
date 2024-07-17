package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.parser.AST.Arguments
import com.kingmang.lazurite.parser.AST.Statements.Statement
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

class ClassInstanceBuilder(
    private val className: String
) {

    private val thisMap = LzrMap(10)
    private var constructor: ClassMethod? = null
    private var toString: UserDefinedFunction? = null

    fun addField(name: String, value: LzrValue) {
        thisMap[name] = value
    }

    fun addMethod(name: String, arguments: Arguments, body: Statement) {
        val method = ClassMethod(arguments, body, thisMap)
        if (name == "toString") {
            toString = method
        }
        if (name == className) {
            constructor = method
        }
        thisMap[name] = method
    }

    fun build(args: Array<LzrValue>): ClassInstanceValue {
        val instance = ClassInstanceValue(className, thisMap, toString)
        constructor?.execute(*args)
        return instance
    }
}
