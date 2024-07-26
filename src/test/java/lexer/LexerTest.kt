package lexer

import com.kingmang.lazurite.parser.impl.LexerImplementation
import com.kingmang.lazurite.parser.tokens.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LexerTest {
    @Test
    fun testLexerStringTemplate() {
        var input = """
                a = 2
                print ("a = ${'$'}a = ${'$'}{a + 4}")
                
                """.trimIndent()
        var expList = Helper.list(
            TokenType.WORD,
            TokenType.EQ,
            TokenType.NUMBER,
            TokenType.PRINT,
            TokenType.LPAREN,
            TokenType.TEXT,
            TokenType.PLUS,
            TokenType.WORD,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.RPAREN,
            TokenType.PLUS,
            TokenType.TEXT,
            TokenType.PLUS,
            TokenType.WORD,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.PLUS,
            TokenType.NUMBER,
            TokenType.RPAREN,
            TokenType.RPAREN
        )
        var result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)

        input = """
                a = 14
                b = 88
                c = "ab = ${'$'}a${'$'}b"
                print("${'$'}c = Procc")
                
                """.trimIndent()

        expList = Helper.list(
            TokenType.WORD,
            TokenType.EQ,
            TokenType.NUMBER,
            TokenType.WORD,
            TokenType.EQ,
            TokenType.NUMBER,
            TokenType.WORD,
            TokenType.EQ,
            TokenType.TEXT,
            TokenType.PLUS,
            TokenType.WORD,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.RPAREN,
            TokenType.PLUS,
            TokenType.WORD,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.RPAREN,
            TokenType.PRINT,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.LPAREN,
            TokenType.WORD,
            TokenType.RPAREN,
            TokenType.PLUS,
            TokenType.TEXT,
            TokenType.RPAREN
        )
        result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)
    }

    @Test
    fun testNums() {
        val input = """
                print  (1_000_000)
                
                """.trimIndent()
        val expList = Helper.list(TokenType.PRINT, TokenType.LPAREN, TokenType.NUMBER, TokenType.RPAREN)
        val result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)
    }

    @Test
    fun testLexer() {
        val input = """
                print  ("Hello")
                
                """.trimIndent()
        val expList = Helper.list(TokenType.PRINT, TokenType.LPAREN, TokenType.TEXT, TokenType.RPAREN)
        val result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)
    }

    @Test
    fun testOperators() {
        val input = "=+-*/%<>!&|"
        val expList = Helper.list(
            TokenType.EQ,
            TokenType.PLUS,
            TokenType.MINUS,
            TokenType.STAR,
            TokenType.SLASH,
            TokenType.PERCENT,
            TokenType.LT,
            TokenType.GT,
            TokenType.EXCL,
            TokenType.AMP,
            TokenType.BAR
        )
        val result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)
    }

    @Test
    fun testString() {
        val input = "\"1\\\"2\""
        val expList = Helper.list(TokenType.TEXT)
        val result = LexerImplementation.tokenize(input)
        Helper.assertTokens(expList, result)
        Assertions.assertEquals("1\"2", result[0].text)
    }
}