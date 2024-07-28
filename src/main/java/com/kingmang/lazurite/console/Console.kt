package com.kingmang.lazurite.console

import com.kingmang.lazurite.console.output.Output
import com.kingmang.lazurite.console.output.impl.SystemOutput
import com.kingmang.lazurite.core.CallStack
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

object Console {
    @JvmStatic
    var output: Output = SystemOutput()

    @JvmStatic
    fun newline(): String =
        output.newline()

    @JvmStatic
    fun print(value: String) =
        output.print(value)

    @JvmStatic
    fun print(value: Any?) =
        output.print(value)

    @JvmStatic
    fun println() =
        output.println()

    @JvmStatic
    fun println(value: String?) =
        output.println(value)

    @JvmStatic
    fun println(value: Any?) =
        output.println(value)

    @JvmStatic
    fun text(): String =
        output.toString()

    @JvmStatic
    fun error(throwable: Throwable) =
        output.error(throwable)

    @JvmStatic
    fun error(value: CharSequence) =
        output.error(value)

    @JvmStatic
    fun handleException(thread: Thread, throwable: Throwable) {
        val baos = ByteArrayOutputStream()
        PrintStream(baos).use { ps ->
            ps.printf("%s in %s%n", throwable.message, thread.name)
            for (call in CallStack.getCalls()) {
                ps.printf("\tat %s%n", call)
            }
            ps.println()
            throwable.printStackTrace(ps)
            ps.flush()
        }
        error(baos.toString("UTF-8"))
    }

    @JvmStatic
    fun fileInstance(path: String): File =
        output.fileInstance(path)
}
