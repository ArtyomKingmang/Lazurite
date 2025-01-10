package com.kingmang.lazurite.parser.impl

import com.kingmang.lazurite.core.Arguments.check
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.Types.typeToString
import com.kingmang.lazurite.core.throwTypeCastException
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ILexer
import com.kingmang.lazurite.parser.standard.Standard
import com.kingmang.lazurite.parser.standard.Standard.*
import com.kingmang.lazurite.parser.tokens.Token
import com.kingmang.lazurite.parser.tokens.TokenType
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.*
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.fromBoolean
import com.kingmang.lazurite.runtime.values.LzrNumber.Companion.of
import java.util.regex.Pattern

class LexerImplementation(private val input: String) : ILexer {
    private val length = input.length
    private val tokens: MutableList<Token> = ArrayList()
    private val buffer = StringBuilder()

    private var pos = 0
    private var row: Int
    private var col = 1

    override fun tokenize(): List<Token> {
        while (pos < length) {
            val current = peek(0)
            if (Character.isDigit(current)) tokenizeNumber()
            else if (isLZRIdentifier(current)) tokenizeWord()
            else if (current == '`') tokenizeExtendedWord()
            else if (current == '"') tokenizeText()
            else if (current == '#') {
                next()
                tokenizeHexNumber()
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator()
            } else {
                // whitespaces
                next()
            }
        }
        return tokens
    }

    private fun tokenizeNumber() {
        clearBuffer()
        var current = peek(0)
        if (current == '0' && (peek(1) == 'x' || (peek(1) == 'X'))) {
            next()
            next()
            tokenizeHexNumber()
            return
        }
        while (true) {
            if (current == '_') {
                current = next()
                continue
            }
            if (current == '.') {
                if (buffer.indexOf(".") != -1) throw error("Invalid float number")
            } else if (!Character.isDigit(current)) {
                break
            }
            buffer.append(current)
            current = next()
        }
        if (current == 'f') {
            next()
            addToken(TokenType.FLOAT_NUM, buffer.toString())
        } else if (current == 'b') {
            next()
            addToken(TokenType.BYTE_NUM, buffer.toString())
        } else if (current == 'l') {
            next()
            addToken(TokenType.LONG_NUM, buffer.toString())
        } else if (current == 'i') {
            next()
            addToken(TokenType.INT_NUM, buffer.toString())
        } else if (current == 'd') {
            next()
            addToken(TokenType.DOUBLE_NUM, buffer.toString())
        } else if (current == 's') {
            next()
            addToken(TokenType.SHORT_NUM, buffer.toString())
        } else addToken(TokenType.NUMBER, buffer.toString())
    }

    private fun tokenizeHexNumber() {
        clearBuffer()

        var current = peek(0)
        while (isHexNumber(current) || (current == '_')) {
            if (current != '_') buffer.append(current)

            current = next()
        }

        if (buffer.length > 0) addToken(TokenType.HEX_NUMBER, buffer.toString())
    }

    private fun tokenizeOperator() {
        var current = peek(0)

        if (current == '/') {
            if (peek(1) == '/') {
                next()
                next()
                tokenizeComment()
                return
            } else if (peek(1) == '*') {
                next()
                next()
                tokenizeMultilineComment()
                return
            }
        }

        clearBuffer()
        while (true) {
            val text = buffer.toString()
            if (!text.isEmpty() && !OPERATORS!!.containsKey(text + current)) {
                addToken(OPERATORS!![text]!!)
                return
            }
            buffer.append(current)
            current = next()
        }
    }

    private fun tokenizeWord() {
        clearBuffer()
        buffer.append(peek(0))
        var current = next()

        while (isLZRIdentifierPart(current)) {
            buffer.append(current)
            current = next()
        }

        val word = buffer.toString()

        if (KEYWORDS.containsKey(word)) addToken(KEYWORDS[word]!!)
        else addToken(TokenType.WORD, word)
    }

    private fun tokenizeExtendedWord() {
        next() // skip `
        clearBuffer()

        var current = peek(0)
        while (current != '`') {
            if (current == '\u0000') throw error("Reached end of file while parsing extended word.")
            if (current == '\n' || current == '\r') throw error("Reached end of line while parsing extended word.")
            buffer.append(current)
            current = next()
        }

        next() // skip closing `
        addToken(TokenType.WORD, buffer.toString())
    }

    private fun tokenizeText() {
        next() // skip "
        clearBuffer()
        var current = peek(0)

        while (true) {
            if (current == '\\') {
                current = next()
                when (current) {
                    '"' -> {
                        current = next()
                        buffer.append('"')
                        continue
                    }

                    '0' -> {
                        current = next()
                        buffer.append('\u0000')
                        continue
                    }

                    'b' -> {
                        current = next()
                        buffer.append('\b')
                        continue
                    }

                    'f' -> {
                        current = next()
                        buffer.append('\u000c')
                        continue
                    }

                    'n' -> {
                        current = next()
                        buffer.append('\n')
                        continue
                    }

                    'r' -> {
                        current = next()
                        buffer.append('\r')
                        continue
                    }

                    't' -> {
                        current = next()
                        buffer.append('\t')
                        continue
                    }

                    'u' -> {
                        val rollbackPosition = pos
                        while (current == 'u') current = next()
                        var escapedValue = 0
                        var i = 12
                        while (i >= 0 && escapedValue != -1) {
                            escapedValue = if (isHexNumber(current)) {
                                escapedValue or (current.digitToIntOrNull(16) ?: -1 shl i)
                            } else {
                                -1
                            }
                            current = next()
                            i -= 4
                        }
                        if (escapedValue >= 0) {
                            buffer.append(escapedValue.toChar())
                        } else {
                            // rollback
                            buffer.append("\\u")
                            pos = rollbackPosition
                        }
                        continue
                    }
                }
                buffer.append('\\')
                continue
            }
            if (current == '"') break
            if (current == '\u0000') throw error("Reached end of file while parsing text string.")
            buffer.append(current)
            current = next()
        }

        next() // skip closing "
        processStringTemplate(buffer.toString())
    }

    /**
     * Обрабатывает входную строку и проводит над ней полонизацию, добавляя необходимые токены.
     *
     *
     * Данный метод принимает строку, содержащую текст и шаблоны, обозначенные символом
     * '$'. Он разделяет строку на части, сохраняя текст и заменяя шаблоны на
     * соответствующие переменные, формируя результат в виде конкатенации строк.
     *
     *
     * Например, для входной строки "a b c $d" метод вернет "a b c " + d.
     * <pre>`String input = "a b c $d";
     * processStringTemplate(input);
     * // Результат (в токенах): [TEXT a b c , PLUS, WORD d]
    `</pre> *
     *
     * @param in входная строка, содержащая текст и шаблоны для замены
     */
    private fun processStringTemplate(`in`: String) {
        if (`in`.isEmpty()) {
            addToken(TokenType.TEXT, `in`)
            return
        }

        val matcher = STR_TEMPLATE_STANDART_PATTERN.matcher(`in`)
        var codeInStringFlag = false
        var lastEndIndex = 0
        var matcherCount = 0

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()

            val text = `in`.substring(lastEndIndex, start)
            val word: String

            if (`in`[start + 1] == '{') {
                // Шаблон вида ${...}
                word = `in`.substring(start + 2, end - 1)
                codeInStringFlag = true
            } else {
                // Шаблон вида $...
                word = `in`.substring(start + 1, end)
            }

            if (matcherCount > 0) {
                addToken(TokenType.PLUS)
            }

            if (!text.isEmpty()) {
                addToken(TokenType.TEXT, text)
                addToken(TokenType.PLUS)
            }

            addToken(TokenType.WORD, "str")
            addToken(TokenType.LPAREN)
            if (!codeInStringFlag) addToken(TokenType.WORD, word)
            else {
                val tokens = tokenize(word)
                this.tokens.addAll(tokens)
            }
            addToken(TokenType.RPAREN)

            lastEndIndex = end
            matcherCount++
        }

        if (matcherCount == 0) addToken(TokenType.TEXT, `in`)
        else if (lastEndIndex < `in`.length) {
            if (matcherCount > 0) addToken(TokenType.PLUS)

            val remainingText = `in`.substring(lastEndIndex)
            addToken(TokenType.TEXT, remainingText)
        }
    }


    private fun tokenizeComment() {
        var current = peek(0)

        while ("\r\n\u0000".indexOf(current) == -1) current = next()
    }

    private fun tokenizeMultilineComment() {
        var current = peek(0)

        while (current != '*' || peek(1) != '/') {
            if (current == '\u0000') throw error("Reached end of file while parsing multiline comment")
            current = next()
        }

        next() // *
        next() // /
    }

    private fun isLZRIdentifierPart(current: Char): Boolean {
        return (Character.isLetterOrDigit(current) || (current == '_') || (current == '$'))
    }

    private fun isLZRIdentifier(current: Char): Boolean {
        return (Character.isLetter(current) || (current == '_') || (current == '$'))
    }

    private fun clearBuffer() {
        buffer.setLength(0)
    }

    private fun next(): Char {
        pos++
        val result = peek(0)
        if (result == '\n') {
            row++
            col = 1
        } else col++
        return result
    }

    private fun peek(relativePosition: Int): Char {
        val position = pos + relativePosition
        if (position >= length) return '\u0000'
        return input[position]
    }

    private fun addToken(type: TokenType, text: String = "") {
        tokens.add(Token(type, text, row, col))
    }

    private fun error(text: String): LzrException {
        return LzrException("Lexer exception", text)
    }

    init {
        row = col
    }

    companion object {
        private const val OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:"
        private var OPERATORS: MutableMap<String, TokenType>? = null
        private val KEYWORDS: MutableMap<String, TokenType> = HashMap()
        private val tokenTypes = TokenType.entries.toTypedArray()

        private val STR_TEMPLATE_STANDART_PATTERN: Pattern =
            Pattern.compile("(?<!\\\\)\\$\\{.*?}|\\$(?!\\{.*?})[a-zA-Z]+")

        private val keywords = arrayOf(
            "assert",
            "do",
            "enum",
            "macro",
            "try",
            "catch",
            "throw",
            "print",
            "println",
            "if",
            "else",
            "while",
            "for",
            "break",
            "continue",
            "func",
            "return",
            "using",
            "match",
            "case",
            "class",
            "new"
        )

        fun tokenize(input: String): List<Token> {
            return LexerImplementation(input).tokenize()
        }

        fun getKeywords(): Set<String> {
            return KEYWORDS.keys
        }

        private fun isHexNumber(current: Char): Boolean {
            return ('0' <= current && current <= '9')
                    || ('a' <= current && current <= 'f')
                    || ('A' <= current && current <= 'F')
        }

        //adding keywords from the keywords array to map KEYWORDS
        init {
            for (i in keywords.indices) {
                if (i < tokenTypes.size) {
                    KEYWORDS[keywords[i]] =
                        tokenTypes[i]
                } else {
                    System.err.print("Not enough token types for all tokens")
                    break
                }
            }

            types()
            convertTypes()
            standard()
        }

        fun types() {
            val type = LzrMap(6)
            type["object"] = of(Types.OBJECT)
            type["number"] = of(Types.NUMBER)
            type["string"] = of(Types.STRING)
            type["array"] = of(Types.ARRAY)
            type["map"] = of(Types.MAP)
            type["function"] = of(Types.FUNCTION)
            define("type", type)
            Keyword.put(
                "typeof",
                Function { args: Array<out LzrValue> -> of(args[0].type()) })
        }

        fun convertTypes() {
            Keyword.put("str", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                LzrString(args[0].asString())
            })
            Keyword.put("char", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__char__")
                }
                LzrString(args[0].asInt().toChar().toString())
            })

            Keyword.put("num", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                of(args[0].asNumber())
            })
            Keyword.put("byte", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__byte__")
                } else {
                    return@Function of(args[0].asInt().toByte().toInt())
                }
            })
            Keyword.put("short", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__short__")
                } else {
                    return@Function of(args[0].asInt().toShort().toInt())
                }
            })
            Keyword.put("int", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                of(args[0].asInt())
            })
            Keyword.put("long", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__long__")
                } else {
                    return@Function of(args[0].asNumber().toLong())
                }
            })
            Keyword.put("float", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__float__")
                } else {
                    return@Function of(args[0].asNumber().toFloat())
                }
            })
            Keyword.put("double", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                if (args[0].type() == Types.CLASS) {
                    val classInstance = args[0] as ClassInstanceValue
                    return@Function classInstance.callMethod("__double__")
                } else {
                    return@Function of(args[0].asNumber())
                }
            })

            Keyword.put("bool", Function { args: Array<out LzrValue> ->
                check(1, args.size)
                when (args[0].type()) {
                    Types.NUMBER -> return@Function fromBoolean(
                        (args[0] as LzrNumber).asBoolean()
                    )

                    Types.STRING -> return@Function fromBoolean(
                        !(args[0] as LzrString).isEmpty()
                    )

                    Types.ARRAY -> return@Function fromBoolean(
                        !(args[0] as LzrArray).isEmpty()
                    )

                    Types.MAP -> return@Function fromBoolean(
                        !(args[0] as LzrMap).isEmpty()
                    )

                    Types.FUNCTION -> return@Function LzrNumber.ONE // true
                    Types.CLASS -> return@Function (args[0] as ClassInstanceValue).callMethod("__bool__")
                    else -> {
                        throwTypeCastException(args[0].asString(), "bool")
                        return@Function LzrNull
                    }
                }
            })
        }

        private fun standard() {
            define("null", LzrNull)


            Keyword.put("getAttr", Function { args: Array<out LzrValue> ->
                check(2, args.size)
                if (args[0].type() == Types.CLASS) {
                    return@Function (args[0] as ClassInstanceValue).baseGet(args[1])
                } else {
                    throw LzrException(
                        "TypeException", "The getAttr function expected a class, but got " + typeToString(
                            args[0].type()
                        )
                    )
                }
            })
            Keyword.put("setAttr", Function { args: Array<out LzrValue> ->
                check(3, args.size)
                if (args[0].type() == Types.CLASS) {
                    (args[0] as ClassInstanceValue).baseSet(args[1], args[2])
                } else {
                    throw LzrException(
                        "TypeException", "The setAttr function expected a class, but got " + typeToString(
                            args[0].type()
                        )
                    )
                }
                LzrNull
            })
            Keyword.put("repeat", repeat())
            Keyword.put("sortBy", sortBy())
            Keyword.put("charAt", charAt())
            Keyword.put("equals", equal())
            Keyword.put("combine", combine())
            Keyword.put("reduce", reduce())
            Keyword.put("map", map())
            Keyword.put("Array", Standard.Array())
            Keyword.put("echo", echo())
            Keyword.put("readln", input())
            Keyword.put("length", length())
            Keyword.put("getBytes", Function { args: Array<out LzrValue> -> Standard.string.getBytes(args) })
            Keyword.put("sprintf", sprintf())
            Keyword.put("range", range())
            Keyword.put("substring", substr())
            Keyword.put("foreach", foreach())
            Keyword.put("split", split())
            Keyword.put("filter", filter(false))
        }

        init {
            OPERATORS = HashMap()

            (OPERATORS as HashMap<String, TokenType>)["+"] = TokenType.PLUS
            (OPERATORS as HashMap<String, TokenType>)["-"] = TokenType.MINUS
            (OPERATORS as HashMap<String, TokenType>)["*"] = TokenType.STAR
            (OPERATORS as HashMap<String, TokenType>)["/"] = TokenType.SLASH
            (OPERATORS as HashMap<String, TokenType>)["%"] = TokenType.PERCENT
            (OPERATORS as HashMap<String, TokenType>)["("] = TokenType.LPAREN
            (OPERATORS as HashMap<String, TokenType>)[")"] = TokenType.RPAREN
            (OPERATORS as HashMap<String, TokenType>)["["] = TokenType.LBRACKET
            (OPERATORS as HashMap<String, TokenType>)["]"] = TokenType.RBRACKET
            (OPERATORS as HashMap<String, TokenType>)["{"] = TokenType.LBRACE
            (OPERATORS as HashMap<String, TokenType>)["}"] = TokenType.RBRACE
            (OPERATORS as HashMap<String, TokenType>)["="] = TokenType.EQ
            (OPERATORS as HashMap<String, TokenType>)["<"] = TokenType.LT
            (OPERATORS as HashMap<String, TokenType>)[">"] = TokenType.GT
            (OPERATORS as HashMap<String, TokenType>)["."] = TokenType.DOT
            (OPERATORS as HashMap<String, TokenType>)[","] = TokenType.COMMA
            (OPERATORS as HashMap<String, TokenType>)["^"] = TokenType.CARET
            (OPERATORS as HashMap<String, TokenType>)["~"] = TokenType.TILDE
            (OPERATORS as HashMap<String, TokenType>)["?"] = TokenType.QUESTION
            (OPERATORS as HashMap<String, TokenType>)[":"] = TokenType.COLON

            (OPERATORS as HashMap<String, TokenType>)["!"] = TokenType.EXCL
            (OPERATORS as HashMap<String, TokenType>)["&"] = TokenType.AMP
            (OPERATORS as HashMap<String, TokenType>)["|"] = TokenType.BAR

            (OPERATORS as HashMap<String, TokenType>)["=="] = TokenType.EQEQ
            (OPERATORS as HashMap<String, TokenType>)["!="] = TokenType.EXCLEQ
            (OPERATORS as HashMap<String, TokenType>)["<="] = TokenType.LTEQ
            (OPERATORS as HashMap<String, TokenType>)[">="] = TokenType.GTEQ

            (OPERATORS as HashMap<String, TokenType>)["+="] = TokenType.PLUSEQ
            (OPERATORS as HashMap<String, TokenType>)["-="] = TokenType.MINUSEQ
            (OPERATORS as HashMap<String, TokenType>)["*="] = TokenType.STAREQ
            (OPERATORS as HashMap<String, TokenType>)["/="] = TokenType.SLASHEQ
            (OPERATORS as HashMap<String, TokenType>)["%="] = TokenType.PERCENTEQ
            (OPERATORS as HashMap<String, TokenType>)["&="] = TokenType.AMPEQ
            (OPERATORS as HashMap<String, TokenType>)["^="] = TokenType.CARETEQ
            (OPERATORS as HashMap<String, TokenType>)["|="] = TokenType.BAREQ
            (OPERATORS as HashMap<String, TokenType>)["::="] = TokenType.COLONCOLONEQ
            (OPERATORS as HashMap<String, TokenType>)["<<="] = TokenType.LTLTEQ
            (OPERATORS as HashMap<String, TokenType>)[">>="] = TokenType.GTGTEQ
            (OPERATORS as HashMap<String, TokenType>)[">>>="] = TokenType.GTGTGTEQ

            (OPERATORS as HashMap<String, TokenType>)["++"] = TokenType.PLUSPLUS
            (OPERATORS as HashMap<String, TokenType>)["--"] = TokenType.MINUSMINUS

            (OPERATORS as HashMap<String, TokenType>)["::"] = TokenType.COLONCOLON

            (OPERATORS as HashMap<String, TokenType>)["&&"] = TokenType.AMPAMP
            (OPERATORS as HashMap<String, TokenType>)["||"] = TokenType.BARBAR

            (OPERATORS as HashMap<String, TokenType>)["<<"] = TokenType.LTLT
            (OPERATORS as HashMap<String, TokenType>)[">>"] = TokenType.GTGT
            (OPERATORS as HashMap<String, TokenType>)[">>>"] = TokenType.GTGTGT

            (OPERATORS as HashMap<String, TokenType>)["@"] = TokenType.AT
            (OPERATORS as HashMap<String, TokenType>)["@="] = TokenType.ATEQ
            (OPERATORS as HashMap<String, TokenType>)[".."] = TokenType.DOTDOT
            (OPERATORS as HashMap<String, TokenType>)["**"] = TokenType.STARSTAR
            (OPERATORS as HashMap<String, TokenType>)["^^"] = TokenType.CARETCARET
            (OPERATORS as HashMap<String, TokenType>)["?:"] = TokenType.QUESTIONCOLON
            (OPERATORS as HashMap<String, TokenType>)["??"] =
                TokenType.QUESTIONQUESTION
        }
    }
}
