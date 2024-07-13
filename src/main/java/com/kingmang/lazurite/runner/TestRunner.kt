package com.kingmang.lazurite.runner

import com.kingmang.lazurite.utils.Handler.Run

object TestRunner {

    @JvmStatic
    fun main(args: Array<String>) {
        val path = "test/"

        val filename = "test.lzr"
        val builder = path + filename

        Run(builder)
    }
}
