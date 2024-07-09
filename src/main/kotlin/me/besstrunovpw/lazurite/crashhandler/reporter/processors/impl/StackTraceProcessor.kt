package me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl

import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor
import java.io.PrintWriter
import java.io.StringWriter

class StackTraceProcessor : ICrashProcessor {

    override fun getName(): String {
        return "stacktrace"
    }

    override fun proceed(throwable: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        return sw.toString()
    }

}
