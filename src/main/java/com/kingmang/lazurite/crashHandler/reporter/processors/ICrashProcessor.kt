package com.kingmang.lazurite.crashHandler.reporter.processors

/**
 * The [ICrashProcessor] interface defines the contract for processing crash information.
 * Implementations of this interface will perform specific operations on a given [Throwable]
 * and return a string as a result.
 */
interface ICrashProcessor {

    /**
     * The name of the processor.
     */
    val name: String

    /**
     * Processes the given [Throwable] and returns a string as a result.
     * This method performs specific operations on the throwable to generate the result.
     *
     * @param throwable The [Throwable] representing the critical error.
     * @return A string result of processing the throwable.
     */
    fun proceed(throwable: Throwable): String

}
