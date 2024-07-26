package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrValue

fun Array<out LzrValue>.check(expected: Int) {
    if (expected == size) return
    throwMismatchException("$expected ${pluralize(expected)} expected, got $size")
}

fun Array<out LzrValue>.checkAtLeast(expected: Int) {
    if (expected <= size) return
    throwMismatchException("At least $expected ${pluralize(expected)} expected, got $size")
}

fun Array<out LzrValue>.checkOrOr(expectedOne: Int, expectedTwo: Int) {
    if (expectedOne == size || expectedTwo == size) return
    throwMismatchException("$expectedOne or $expectedTwo arguments expected, got $size")
}

fun Array<out LzrValue>.checkRange(from: Int, to: Int) {
    if (size in from..to) return
    throwMismatchException("From $from to $to arguments expected, got $size")
}

private fun throwMismatchException(text: String): Nothing {
    throw LzrException("ArgumentsMismatchException", text)
}

private fun pluralize(count: Int): String {
    return if ((count == 1)) "argument" else "arguments"
}