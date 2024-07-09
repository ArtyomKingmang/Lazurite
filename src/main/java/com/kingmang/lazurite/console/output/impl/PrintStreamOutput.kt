package com.kingmang.lazurite.console.output.impl

import com.kingmang.lazurite.console.output.Output
import java.io.File
import java.io.PrintStream

class PrintStreamOutput (
    val out: PrintStream = System.out,
    val err: PrintStream = System.err,
) : Output {
    override fun newline(): String {
        return System.lineSeparator()
    }

    override fun print(value: String?) {
        out.print(value)
    }

    override fun println(value: String?) {
        out.println(value)
    }

    override fun getText(): String {
        return ""
    }

    override fun error(value: CharSequence?) {
        err.println(value)
    }

    override fun fileInstance(path: String?): File {
        return File(path!!)
    }
}
