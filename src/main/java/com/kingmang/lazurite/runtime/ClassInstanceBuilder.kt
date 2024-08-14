package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.parser.ast.Arguments
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

class ClassInstanceBuilder(private val className: String) {
    private var constructor: ClassMethod? = null
    private val classInstanceValue: ClassInstanceValue = ClassInstanceValue(this.className, LzrMap(10))
    fun addField(name: String, value: LzrValue) {
        this.classInstanceValue[name] = value
    }

    fun addMethod(name: String, arguments: Arguments, body: Statement) {
        val method = ClassMethod(arguments, body, this.classInstanceValue)
        if (name == className)
            this.constructor = method
        this.classInstanceValue[name] = method
    }

    fun build(args: Array<LzrValue>): ClassInstanceValue =
        this.classInstanceValue.also {
            this.constructor?.execute(*args)
        }
}
