package me.besstrunovpw.lazurite.crashhandler

import me.besstrunovpw.lazurite.crashhandler.reporter.ICrashReporter

object CrashHandler {

    private var crashReporter: ICrashReporter? = null

    fun register(
        crashReporter: ICrashReporter
    ) {
        this.crashReporter = crashReporter
    }

    fun getCrashReporter(): ICrashReporter = this.crashReporter!!

    fun proceed(throwable: Throwable) {
        println(crashReporter!!.report(throwable))
    }

}