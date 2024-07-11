package com.kingmang.lazurite.crashHandler.reporter

import com.kingmang.lazurite.crashHandler.reporter.processors.ICrashProcessor

/**
 * The [ICrashReporter] interface defines the contract for reporting critical interpreter crashes.
 * It provides methods to add processors that can process the crash information
 * and to generate a structured crash report.
 */
interface ICrashReporter {

    /**
     * Adds a processor to the crash reporter.
     * The added processor will be invoked during the [report] method execution.
     *
     * @param processor The [ICrashProcessor] to be added.
     * @return The current instance of [ICrashReporter] for method chaining.
     */
    fun addProcessor(processor: ICrashProcessor): ICrashReporter

    /**
     * Generates a crash report for the given [Throwable] and returns it as a string.
     * This method will invoke all added processors to process the crash information.
     *
     * @param throwable The [Throwable] representing the critical error.
     * @return A structured crash report as a string.
     */
    fun report(throwable: Throwable): String

}