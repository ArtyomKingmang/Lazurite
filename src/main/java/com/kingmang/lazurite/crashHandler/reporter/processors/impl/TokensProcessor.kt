package com.kingmang.lazurite.crashHandler.reporter.processors.impl

import com.kingmang.lazurite.parser.tokens.Token
import com.kingmang.lazurite.parser.tokens.TokenType
import com.kingmang.lazurite.crashHandler.reporter.processors.ICrashProcessor

class TokensProcessor (
    private var tokens: List<Token>
) : ICrashProcessor {

    override val name: String
        get() = "tokens"

    override fun proceed(throwable: Throwable): String {
        return """
            Summary: ${tokens.size} tokens
            Tokens: ${tokens.joinToString(", ") { it.toString() }}
            
            TEXT: ${tokens.filter { it.type == TokenType.TEXT }.joinToString(", ") { "[${it.col}, ${it.row}] \"${it.text}\"" }}
            WORD: ${tokens.filter { it.type == TokenType.WORD }.joinToString(", ") { "[${it.col}, ${it.row}] \"${it.text}\"" }}
        """.trimIndent()
    }

}
