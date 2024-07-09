package me.besstrunovpw.lazurite.crashhandler

import me.besstrunovpw.lazurite.crashhandler.reporter.ICrashReporter
import me.besstrunovpw.lazurite.crashhandler.reporter.output.IReportOutput

object CrashHandler {

    private var crashReporter: ICrashReporter? = null
    private val reportOutputs: MutableList<IReportOutput> = mutableListOf()

    fun register(
        crashReporter: ICrashReporter,
        vararg outputs: IReportOutput
    ) {
        this.crashReporter = crashReporter
        this.reportOutputs.addAll(outputs)
    }

    fun getCrashReporter(): ICrashReporter = this.crashReporter!!

    fun proceed(throwable: Throwable) {
        val report: String = crashReporter!!.report(throwable)

        for(output: IReportOutput in reportOutputs)
            output.out(report)
    }

}