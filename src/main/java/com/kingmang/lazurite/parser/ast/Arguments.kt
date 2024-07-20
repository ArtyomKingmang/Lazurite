package com.kingmang.lazurite.parser.ast

class Arguments(
    private val arguments: List<Argument>,
    val requiredCount: Int
) : Iterable<Argument> {

    fun get(index: Int): Argument {
        return arguments[index]
    }

    fun size(): Int {
        return arguments.size
    }

    override fun iterator(): Iterator<Argument> {
        return arguments.iterator()
    }

    override fun toString(): String {
        return arguments.joinToString(separator = ", ", prefix = "(", postfix = ")")
    }
}

