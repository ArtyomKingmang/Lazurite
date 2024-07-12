package com.kingmang.lazurite.utils

import java.io.*

object Loader {

    @JvmStatic
    @Throws(IOException::class)
    fun readSource(name: String): String {
        return Loader::class.java.getResourceAsStream("/$name")?.readAndCloseStream()
            ?: FileInputStream(name).readAndCloseStream()
    }

    private fun InputStream.readAndCloseStream(): String {
        return reader().use { it.readText() }
    }
}
