package com.kingmang.lazurite.crashHandler.reporter.processors.impl

import com.kingmang.lazurite.crashHandler.reporter.processors.ICrashProcessor
import java.io.PrintWriter
import java.io.StringWriter

class StackTraceProcessor : ICrashProcessor {

    override val name: String
        get() = "stacktrace"

    override fun proceed(throwable: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        return sw.toString()
    }

}
