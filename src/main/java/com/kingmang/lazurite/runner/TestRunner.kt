package com.kingmang.lazurite.runner

import com.kingmang.lazurite.utils.Handler.Run

object TestRunner {

    @JvmStatic
    fun main(args: Array<String>) {
        Run("test/test.lzr")
    }
}

