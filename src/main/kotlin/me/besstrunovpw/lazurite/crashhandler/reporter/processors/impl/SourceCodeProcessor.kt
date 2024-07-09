package me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl

import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor

class SourceCodeProcessor (
    private val source: String
) : ICrashProcessor {

    override val name: String
        get() = "source"

    override fun proceed(throwable: Throwable): String {
        return source
    }

}
