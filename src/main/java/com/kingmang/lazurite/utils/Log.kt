package com.kingmang.lazurite.utils

import java.io.FileWriter

object Log {

    @JvmStatic
    fun append(str: String) {
        FileWriter("log.txt", true).use { writer ->
            writer.write(str)
            writer.appendLine()
            writer.flush()
        }
    }

    @JvmStatic
    fun clear() {
        FileWriter("log.txt", false).use { writer ->
            writer.write("")
            writer.flush()
        }
    }
}