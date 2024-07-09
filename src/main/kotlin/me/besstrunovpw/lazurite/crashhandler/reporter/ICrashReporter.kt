package me.besstrunovpw.lazurite.crashhandler.reporter

import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor

interface ICrashReporter {

    fun addProcessor(processor: ICrashProcessor): ICrashReporter

    fun report(throwable: Throwable): String

}