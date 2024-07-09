package me.besstrunovpw.lazurite.crashhandler.reporter.processors

interface ICrashProcessor {
    val name: String
    fun proceed(throwable: Throwable): String
}
