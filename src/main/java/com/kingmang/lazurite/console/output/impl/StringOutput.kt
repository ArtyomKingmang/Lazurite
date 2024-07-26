package com.kingmang.lazurite.console.output.impl

import com.kingmang.lazurite.console.output.Output
import lombok.AllArgsConstructor
import java.io.File

class StringOutput(private val out: StringBuffer?, private val err: StringBuffer?) : Output {
    constructor(out: StringBuffer? = StringBuffer()) : this(out, out)

    override fun newline(): String =
        System.lineSeparator()

    override fun print(value: String) {
        out!!.append(value)
    }

    override fun println() {
        out!!.append(newline())
    }

    override fun println(value: String) {
        out!!.append(value).append(newline())
    }

    override fun toString(): String {
        return out.toString()
    }

    override fun error(throwable: Throwable) {
        error(throwable.toString())
    }

    override fun error(value: CharSequence) {
        err!!.append(value).append(newline())
    }

    override fun fileInstance(path: String): File =
        File(path)
}