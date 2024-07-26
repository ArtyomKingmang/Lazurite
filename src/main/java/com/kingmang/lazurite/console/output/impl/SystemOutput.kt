package com.kingmang.lazurite.console.output.impl

import com.kingmang.lazurite.console.output.Output
import java.io.File

class SystemOutput : Output {
    private val out = PrintStreamOutput(System.out, System.err)

    override fun newline(): String =
        System.lineSeparator()

    override fun print(value: String) {
        out.print(value)
    }

    override fun error(value: CharSequence) {
        out.error(value)
    }

    override fun fileInstance(path: String): File =
        File(path)
}
