package com.kingmang.lazurite.exceptions

import java.io.PrintStream

open class LzrException(val type: String, message: String) : RuntimeException(message) {
    open fun print(stream: PrintStream) {
        stream.printf("[%s] %s", type, message)
    }
}