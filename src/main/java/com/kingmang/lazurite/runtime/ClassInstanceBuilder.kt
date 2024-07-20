package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.parser.ast.Arguments
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

class ClassInstanceBuilder(private val className: String) {
    private val thisMap = LzrMap(10)
    private var constructor: ClassMethod? = null
    private var toString: UserDefinedFunction? = null

    fun addField(name: String, value: LzrValue) {
        this.thisMap[name] = value
    }

    fun addMethod(name: String, arguments: Arguments, body: Statement) {
        val method = ClassMethod(arguments, body, this.thisMap)
        if (name == "toString")
            this.toString = method
        if (name == className)
            this.constructor = method
        this.thisMap[name] = method
    }

    fun build(args: Array<LzrValue>): ClassInstanceValue =
        ClassInstanceValue(this.className, this.thisMap, this.toString).also {
            this.constructor?.execute(*args)
        }
}
