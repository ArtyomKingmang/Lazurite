package com.kingmang.lazurite.crashHandler.reporter.processors.impl

import com.kingmang.lazurite.crashHandler.reporter.processors.ICrashProcessor

class SourceCodeProcessor (
    private val source: String
) : ICrashProcessor {

    override val name: String
        get() = "source"

    override fun proceed(throwable: Throwable): String {
        return source
    }

}
