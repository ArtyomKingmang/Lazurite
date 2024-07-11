package com.kingmang.lazurite.crashHandler

import com.kingmang.lazurite.crashHandler.reporter.ICrashReporter
import com.kingmang.lazurite.crashHandler.reporter.output.IReportOutput

/**
 * The [CrashHandler] object is responsible for handling critical interpreter crashes.
 * It helps in identifying and reporting critical errors in the interpreter's code.
 *
 * This class utilizes an [ICrashReporter] to structure the crash reports
 * and outputs the reports to various destinations via implementations of [IReportOutput].
 *
 * To use this class, you must first register the crash reporter and report outputs
 * by calling the [register] method. Once registered, you can handle a critical error
 * by calling the [proceed] method.
 *
 * This class maintains a list of registered [IReportOutput] instances
 * and a single instance of [ICrashReporter] to process and output crash reports.
 */
object CrashHandler {

    /**
     * The crash reporter responsible for structuring crash reports.
     */
    private var crashReporter: ICrashReporter? = null

    /**
     * The list of outputs where crash reports will be sent.
     */
    private val reportOutputs: MutableList<IReportOutput> = mutableListOf()

    /**
     * Registers the crash reporter and the report outputs.
     * This method must be called before invoking the [proceed] method.
     *
     * @param crashReporter The crash reporter to be used.
     * @param outputs The outputs where crash reports will be sent.
     * @throws IllegalArgumentException if [crashReporter] is null or [outputs] is empty.
     */
    fun register(
        crashReporter: ICrashReporter,
        vararg outputs: IReportOutput
    ) {
        CrashHandler.crashReporter = crashReporter
        reportOutputs.addAll(outputs)
    }

    /**
     * Returns the currently registered crash reporter.
     *
     * @return The current crash reporter.
     * @throws IllegalStateException if no crash reporter has been registered.
     */
    fun getCrashReporter(): ICrashReporter = crashReporter!!

    /**
     * Processes a critical error by generating a crash report and sending it to all registered outputs.
     *
     * @param throwable The [Throwable] representing the critical error.
     * @throws IllegalStateException if no crash reporter has been registered.
     */
    fun proceed(throwable: Throwable) {
        val report: String = crashReporter!!.report(throwable)

        for(output: IReportOutput in reportOutputs)
            output.out(report)
    }

}