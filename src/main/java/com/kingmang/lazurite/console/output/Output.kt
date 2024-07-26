package com.kingmang.lazurite.console.output

import java.io.File

interface Output {
    fun newline(): String

    fun print(value: String)

    fun print(value: Any?) =
        print(value.toString())

    fun println() =
        print(newline())

    fun println(value: String) {
        print(value)
        println()
    }

    fun println(value: Any?) =
        println(value.toString())

    fun error(throwable: Throwable) =
        error(throwable.toString())

    fun error(value: CharSequence)

    fun fileInstance(path: String): File
}
