package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.LzrException
import lombok.NoArgsConstructor

object Arguments {
    @JvmStatic
    fun check(expected: Int, got: Int) {
        if (got != expected) {
            throw LzrException("ArgumentsMismatchException ", String.format("%d %s expected, got %d", expected, pluralize(expected), got))
        }
    }

    @JvmStatic
    fun checkAtLeast(expected: Int, got: Int) {
        if (got < expected) {
            throw LzrException("ArgumentsMismatchException ", String.format("At least %d %s expected, got %d", expected, pluralize(expected), got))
        }
    }

    @JvmStatic
    fun checkOrOr(expectedOne: Int, expectedTwo: Int, got: Int) {
        if (expectedOne != got && expectedTwo != got) {
            throw LzrException("ArgumentsMismatchException ", String.format("%d or %d arguments expected, got %d", expectedOne, expectedTwo, got))
        }
    }

    @JvmStatic
    fun checkRange(from: Int, to: Int, got: Int) {
        if (from > got || got > to) {
            throw LzrException("ArgumentsMismatchException ", String.format("From %d to %d arguments expected, got %d", from, to, got))
        }
    }

    private fun pluralize(count: Int): String =
        if ((count == 1))
            "argument"
        else "arguments"
}
