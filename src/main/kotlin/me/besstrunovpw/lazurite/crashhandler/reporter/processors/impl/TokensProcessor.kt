package me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl

import com.kingmang.lazurite.parser.Token
import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor

class TokensProcessor (
    private var tokens: List<Token>
) : ICrashProcessor {

    override fun getName(): String {
        return "tokens"
    }

    override fun proceed(throwable: Throwable): String {
        return """
            Summary: ${tokens.size} tokens
            Tokens: ${tokens.joinToString(", ") { it.toString() }}
        """.trimIndent()
    }

}
