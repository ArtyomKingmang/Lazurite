package com.kingmang.lazurite.exceptions.parser

import com.kingmang.lazurite.console.Console.newline

class ParseErrors : Iterable<ParseError?> {
    private val errors: MutableList<ParseError> = ArrayList()

    fun clear() =
        errors.clear()

    fun add(ex: Exception?, line: Int) {
        errors.add(ParseError(line, ex!!))
    }

    fun hasErrors(): Boolean =
        errors.isNotEmpty()

    override fun iterator(): MutableIterator<ParseError> =
        errors.iterator()

    override fun toString(): String {
        val result = StringBuilder()
        errors.forEach { result.append(it).append(newline()) }
        return result.toString()
    }
}
