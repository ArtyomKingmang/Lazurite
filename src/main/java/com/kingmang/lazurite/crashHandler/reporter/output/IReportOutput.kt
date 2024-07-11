package com.kingmang.lazurite.crashHandler.reporter.output

/**
 * The [IReportOutput] interface defines the contract for outputting crash reports.
 * Implementations of this interface will handle the output of the generated crash report.
 */
interface IReportOutput {

    /**
     * Outputs the given crash report.
     * This method is responsible for handling the output destination of the crash report.
     *
     * @param report The crash report as a string. It can be null.
     */
    fun out(report: String)

}
