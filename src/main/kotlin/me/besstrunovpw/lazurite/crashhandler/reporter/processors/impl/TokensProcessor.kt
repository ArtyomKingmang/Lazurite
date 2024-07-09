package me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl

import com.kingmang.lazurite.parser.Token
import com.kingmang.lazurite.parser.TokenType
import me.besstrunovpw.lazurite.crashhandler.reporter.processors.ICrashProcessor

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
