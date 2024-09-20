package com.kingmang.lazurite.runner

import com.kingmang.lazurite.utils.Handler.run

object TestRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        run("test/test.lzr", 5, false)
    }
}

