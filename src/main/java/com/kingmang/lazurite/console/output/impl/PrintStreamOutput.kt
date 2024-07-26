package com.kingmang.lazurite.console.output.impl

import com.kingmang.lazurite.console.output.Output
import java.io.File
import java.io.PrintStream

class PrintStreamOutput (
    private val out: PrintStream = System.out,
    private val err: PrintStream = System.err
) : Output {
    override fun newline(): String =
        System.lineSeparator()

    override fun print(value: String) =
        out.print(value)

    override fun error(value: CharSequence) =
        err.println(value)

    override fun fileInstance(path: String): File =
        File(path)
}
