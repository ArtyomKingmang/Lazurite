package com.kingmang.lazurite.parser.AST

import com.kingmang.lazurite.parser.AST.Expressions.Expression

class ArgumentsBuilder {

    private val arguments = ArrayList<Argument>()

    private var requiredCount = 0

    fun addRequired(name: String) {
        arguments.add(Argument(name))
        requiredCount++
    }

    fun addOptional(name: String, expr: Expression) {
        arguments.add(Argument(name, expr))
    }

    fun build(): Arguments {
        return Arguments(arguments, requiredCount)
    }
}