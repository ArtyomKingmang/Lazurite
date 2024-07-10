package me.besstrunovpw.lazurite.crashhandler.reporter.impl

import com.kingmang.lazurite.runner.RunnerInfo
import me.besstrunovpw.lazurite.crashhandler.reporter.ICrashReporter
import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor
import me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl.StackTraceProcessor

class SimpleCrashReporter : ICrashReporter {

    private var output = StringBuilder()
    private var throwable: Throwable? = null

    private val processors: MutableList<ICrashProcessor> = mutableListOf(
        StackTraceProcessor(),
    )

    private fun prepare(throwable: Throwable) {
        this.output = StringBuilder()
        this.throwable = throwable
    }

    private fun printHeader() {
        val version: String = RunnerInfo.VERSION
        output.append("""
            #
            # A fatal error has been detected:
            #
            #  ${throwable?.message}
            #
            # Lazurite version: $version
            # JVM: ${System.getProperty("java.vendor")}@${System.getProperty("java.version")} (${System.getProperty("java.vm.version")})
            #
            # If you would like to submit a bug report, please visit:
            #    https://github.com/ArtyomKingmang/Lazurite/issues/new
            #
            
            
        """.trimIndent())
    }

    override fun addProcessor(processor: ICrashProcessor): ICrashReporter {
        processors.add(processor)
        return this
    }

    override fun report(throwable: Throwable): String {
        prepare(throwable)
        printHeader()

        for(processor: ICrashProcessor in processors) {
            output.append("""
                ----- ${processor.name.uppercase().split("").filter(String::isNotBlank).joinToString(" ")} -----
            """.trimIndent())

            output.append("\n")
            output.append(processor.proceed(throwable))
            output.append("\n\n")
        }

        return output.toString()
    }

}
