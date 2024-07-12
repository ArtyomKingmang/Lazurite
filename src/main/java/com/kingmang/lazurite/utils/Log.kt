package com.kingmang.lazurite.utils

import java.io.FileWriter

object Log {

    @JvmStatic
    fun append(str: String) {
        try {
            FileWriter("log.txt", true).use { writer ->
                writer.write(str)
                writer.appendLine()
                writer.flush()
            }
        } catch (_: Exception) {
            // do nothing
        }
    }

    @JvmStatic
    fun clear() {
        try {
            FileWriter("log.txt", false).use { writer ->
                writer.write("")
                writer.flush()
            }
        } catch (_: Exception) {
            // do nothing
        }
    }
}