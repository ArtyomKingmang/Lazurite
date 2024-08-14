package com.kingmang.lazurite.parser.impl;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.parser.ILexer;
import com.kingmang.lazurite.parser.tokens.Token;
import com.kingmang.lazurite.parser.tokens.TokenType;
import com.kingmang.lazurite.parser.standard.Standard;
import com.kingmang.lazurite.runtime.ClassInstanceValue;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kingmang.lazurite.core.TypesCastExtKt.throwTypeCastException;

public final class LexerImplementation implements ILexer {

    private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:";
    private static final Map<String, TokenType> OPERATORS;
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    private static final TokenType[] tokenTypes = TokenType.values();

    private static final Pattern STR_TEMPLATE_STANDART_PATTERN = Pattern.compile("(?<!\\\\)\\$\\{.*?}|\\$(?!\\{.*?})[a-zA-Z]+");

    private static final String[] keywords = {
            "assert",
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
    };

    private final String input;
    private final int length;
    private final List<Token> tokens;
    private final StringBuilder buffer;

    private int pos;
    private int row, col;

    public static List<Token> tokenize(String input) {
        return new LexerImplementation(input).tokenize();
    }

    public static Set<String> getKeywords() {
        return KEYWORDS.keySet();
    }

    public LexerImplementation(String input) {
        this.input = input;
        length = input.length();

        tokens = new ArrayList<>();
        buffer = new StringBuilder();
        row = col = 1;
    }

    @NotNull
    public List<Token> tokenize() {
        while (pos < length) {
            final char current = peek(0);
            if (Character.isDigit(current)) tokenizeNumber();
            else if (isLZRIdentifier(current)) tokenizeWord();
            else if (current == '`') tokenizeExtendedWord();
            else if (current == '"') tokenizeText();
            else if (current == '#') {
                next();
                tokenizeHexNumber();
            }
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                // whitespaces
                next();
            }
        }
        return tokens;
    }

    private void tokenizeNumber() {
        clearBuffer();
        char current = peek(0);
        if (current == '0' && (peek(1) == 'x' || (peek(1) == 'X'))) {
            next();
            next();
            tokenizeHexNumber();
            return;
        }
        while (true) {
            if (current == '_') {
                current = next();
                continue;
            }
            if (current == '.') {
                if (buffer.indexOf(".") != -1) throw error("Invalid float number");
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();

        }
        if (current == 'f') {
            next();
            addToken(TokenType.FLOAT_NUM, buffer.toString());
        } else if (current == 'b') {
            next();
            addToken(TokenType.BYTE_NUM, buffer.toString());
        } else if (current == 'l') {
            next();
            addToken(TokenType.LONG_NUM, buffer.toString());
        } else if (current == 'i') {
            next();
            addToken(TokenType.INT_NUM, buffer.toString());
        } else if (current == 'd') {
            next();
            addToken(TokenType.DOUBLE_NUM, buffer.toString());
        } else if (current == 's') {
            next();
            addToken(TokenType.SHORT_NUM, buffer.toString());
        } else
            addToken(TokenType.NUMBER, buffer.toString());
    }
    private void tokenizeHexNumber() {
        clearBuffer();

        char current = peek(0);
        while (isHexNumber(current) || (current == '_')) {
            if (current != '_')
                buffer.append(current);

            current = next();
        }

        if (buffer.length() > 0)
            addToken(TokenType.HEX_NUMBER, buffer.toString());
    }

    private static boolean isHexNumber(char current) {
        return Character.isDigit(current)
                || ('a' <= current && current <= 'f')
                || ('A' <= current && current <= 'F');
    }

    private void tokenizeOperator() {
        char current = peek(0);

        if (current == '/') {
            if (peek(1) == '/') {
                next();
                next();
                tokenizeComment();
                return;
            } else if (peek(1) == '*') {
                next();
                next();
                tokenizeMultilineComment();
                return;
            }
        }

        clearBuffer();
        while (true) {
            final String text = buffer.toString();
            if (!text.isEmpty() && !OPERATORS.containsKey(text + current)) {
                addToken(OPERATORS.get(text));
                return;
            }
            buffer.append(current);
            current = next();
        }
    }

    private void tokenizeWord() {
        clearBuffer();
        buffer.append(peek(0));
        char current = next();

        while (isLZRIdentifierPart(current)) {
            buffer.append(current);
            current = next();
        }

        final String word = buffer.toString();

        if (KEYWORDS.containsKey(word))
            addToken(KEYWORDS.get(word));
        else
            addToken(TokenType.WORD, word);

    }

    private void tokenizeExtendedWord() {
        next();// skip `
        clearBuffer();

        char current = peek(0);
        while (current != '`') {
            if (current == '\0') throw error("Reached end of file while parsing extended word.");
            if (current == '\n' || current == '\r') throw error("Reached end of line while parsing extended word.");
            buffer.append(current);
            current = next();
        }

        next(); // skip closing `
        addToken(TokenType.WORD, buffer.toString());
    }

    private void tokenizeText() {
        next();// skip "
        clearBuffer();
        char current = peek(0);

        while (true) {
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"': current = next(); buffer.append('"'); continue;
                    case '0': current = next(); buffer.append('\0'); continue;
                    case 'b': current = next(); buffer.append('\b'); continue;
                    case 'f': current = next(); buffer.append('\f'); continue;
                    case 'n': current = next(); buffer.append('\n'); continue;
                    case 'r': current = next(); buffer.append('\r'); continue;
                    case 't': current = next(); buffer.append('\t'); continue;
                    case 'u':
                        int rollbackPosition = pos;
                        while (current == 'u') current = next();
                        int escapedValue = 0;
                        for (int i = 12; i >= 0 && escapedValue != -1; i -= 4) {
                            if (isHexNumber(current)) {
                                escapedValue |= (Character.digit(current, 16) << i);
                            } else {
                                escapedValue = -1;
                            }
                            current = next();
                        }
                        if (escapedValue >= 0) {
                            buffer.append((char) escapedValue);
                        } else {
                            // rollback
                            buffer.append("\\u");
                            pos = rollbackPosition;
                        }
                        continue;
                }
                buffer.append('\\');
                continue;
            }
            if (current == '"') break;
            if (current == '\0') throw error("Reached end of file while parsing text string.");
            buffer.append(current);
            current = next();
        }

        next(); // skip closing "
        processStringTemplate(buffer.toString());
    }

    /**
     * Обрабатывает входную строку и проводит над ней полонизацию, добавляя необходимые токены.
     *
     * <p>Данный метод принимает строку, содержащую текст и шаблоны, обозначенные символом
     * '$'. Он разделяет строку на части, сохраняя текст и заменяя шаблоны на
     * соответствующие переменные, формируя результат в виде конкатенации строк.</p>
     *
     * <p>Например, для входной строки "a b c $d" метод вернет "a b c " + d.</p>
     * <pre>{@code
     * String input = "a b c $d";
     * processStringTemplate(input);
     * // Результат (в токенах): [TEXT a b c , PLUS, WORD d]
     * }</pre>
     *
     * @param in входная строка, содержащая текст и шаблоны для замены
     */
    private void processStringTemplate(@NotNull String in) {
        if (in.isEmpty()) {
            addToken(TokenType.TEXT, in);
            return;
        }

        Matcher matcher = STR_TEMPLATE_STANDART_PATTERN.matcher(in);
        boolean codeInStringFlag = false;
        int lastEndIndex = 0;
        int matcherCount = 0;

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            String text = in.substring(lastEndIndex, start);
            String word;

            if (in.charAt(start + 1) == '{') {
                // Шаблон вида ${...}
                word = in.substring(start + 2, end - 1);
                codeInStringFlag = true;
            } else {
                // Шаблон вида $...
                word = in.substring(start + 1, end);
            }

            if (matcherCount > 0) {
                addToken(TokenType.PLUS);
            }

            if (!text.isEmpty()) {
                addToken(TokenType.TEXT, text);
                addToken(TokenType.PLUS);
            }

            addToken(TokenType.WORD, "str");
            addToken(TokenType.LPAREN);
            if (!codeInStringFlag)
                addToken(TokenType.WORD, word);
            else {
                List<Token> tokens = LexerImplementation.tokenize(word);
                this.tokens.addAll(tokens);
            }
            addToken(TokenType.RPAREN);

            lastEndIndex = end;
            matcherCount++;
        }

        if (matcherCount == 0)
            addToken(TokenType.TEXT, in);

        else if (lastEndIndex < in.length()) {
            if (matcherCount > 0)
                addToken(TokenType.PLUS);

            String remainingText = in.substring(lastEndIndex);
            addToken(TokenType.TEXT, remainingText);
        }
    }


    private void tokenizeComment() {
        char current = peek(0);

        while ("\r\n\0".indexOf(current) == -1)
            current = next();
    }

    private void tokenizeMultilineComment() {
        char current = peek(0);

        while (current != '*' || peek(1) != '/') {
            if (current == '\0') throw error("Reached end of file while parsing multiline comment");
            current = next();
        }

        next(); // *
        next(); // /
    }

    private boolean isLZRIdentifierPart(char current) {
        return (Character.isLetterOrDigit(current) || (current == '_') || (current == '$'));
    }

    private boolean isLZRIdentifier(char current) {
        return (Character.isLetter(current) || (current == '_') || (current == '$'));
    }

    private void clearBuffer() {
        buffer.setLength(0);
    }

    private char next() {
        pos++;
        final char result = peek(0);
        if (result == '\n') {
            row++;
            col = 1;
        } else col++;
        return result;
    }

    private char peek(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return input.charAt(position);
    }

    private void addToken(TokenType type) {
        addToken(type, "");
    }

    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text, row, col));
    }

    private LzrException error(String text) {
        return new LzrException("Lexer exception", text);
    }

    //adding keywords from the keywords array to map KEYWORDS
    static {
        for (int i = 0; i < keywords.length; i++) {
            if (i < tokenTypes.length) {
                KEYWORDS.put(keywords[i], tokenTypes[i]);
            } else {
                System.err.print("Not enough token types for all tokens");
                break;
            }
        }

        types();
        convertTypes();
        standard();

    }

    public static void types() {
        LzrMap type = new LzrMap(6);
        type.set("object", LzrNumber.of(Types.OBJECT));
        type.set("number", LzrNumber.of(Types.NUMBER));
        type.set("string", LzrNumber.of(Types.STRING));
        type.set("array", LzrNumber.of(Types.ARRAY));
        type.set("map", LzrNumber.of(Types.MAP));
        type.set("function", LzrNumber.of(Types.FUNCTION));
        Variables.define("type", type);
        Keyword.put("typeof", args -> LzrNumber.of(args[0].type()));
    }

    public static void convertTypes() {
        Keyword.put("str", args -> {
            Arguments.check(1, args.length);
            return new LzrString(args[0].asString());
        });
        Keyword.put("char", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__char__");
            }
            return new LzrString(String.valueOf((char) args[0].asInt()));
        });

        Keyword.put("num", args -> {
            Arguments.check(1, args.length);
            return LzrNumber.of(args[0].asNumber());
        });
        Keyword.put("byte", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__byte__");
            } else {
                return LzrNumber.of((byte) args[0].asInt());
            }
        });
        Keyword.put("short", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__short__");
            } else {
                return LzrNumber.of((short) args[0].asInt());
            }
        });
        Keyword.put("int", args -> {
            Arguments.check(1, args.length);
            return LzrNumber.of(args[0].asInt());
        });
        Keyword.put("long", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__long__");
            } else {
                return LzrNumber.of((long) args[0].asNumber());
            }
        });
        Keyword.put("float", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__float__");
            } else {
                return LzrNumber.of((float) args[0].asNumber());
            }
        });
        Keyword.put("double", args -> {
            Arguments.check(1, args.length);
            if (args[0].type() == Types.CLASS) {
                ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                return classInstance.callMethod("__double__");
            } else {
                return LzrNumber.of(args[0].asNumber());
            }
        });

        Keyword.put("bool", args -> {
            Arguments.check(1, args.length);
            switch (args[0].type()) {
                case Types.NUMBER:
                    return LzrNumber.fromBoolean(
                            ((LzrNumber) args[0]).asBoolean()
                    );
                case Types.STRING:
                    return LzrNumber.fromBoolean(
                            !((LzrString) args[0]).isEmpty()
                    );
                case Types.ARRAY:
                    return LzrNumber.fromBoolean(
                            !((LzrArray) args[0]).isEmpty()
                    );
                case Types.MAP:
                    return LzrNumber.fromBoolean(
                            !((LzrMap) args[0]).isEmpty()
                    );
                case Types.FUNCTION:
                    return LzrNumber.ONE; // true
                case Types.CLASS:
                    return ((ClassInstanceValue) args[0]).callMethod("__bool__");
                default:
                    throwTypeCastException(args[0].asString(), "bool");
                    return LzrNull.INSTANCE;
            }
        });
    }

    private static void standard() {
        Variables.define("null", LzrNull.INSTANCE);

        Keyword.put("getAttr", args -> {
            Arguments.check(2, args.length);
            if (args[0].type() == Types.CLASS) {
                return ((ClassInstanceValue) args[0]).baseGet(args[1]);
            } else {
                throw new LzrException("TypeException", "The getAttr function expected a class, but got " + Types.typeToString(args[0].type()));
            }
        });
        Keyword.put("setAttr", args -> {
           Arguments.check(3, args.length);
           if (args[0].type() == Types.CLASS) {
               ((ClassInstanceValue) args[0]).baseSet(args[1], args[2]);
           } else {
               throw new LzrException("TypeException", "The setAttr function expected a class, but got " + Types.typeToString(args[0].type()));

           }
           return LzrNull.INSTANCE;
        });

        Keyword.put("sortBy", new Standard.sortBy());
        Keyword.put("charAt", new Standard.charAt());
        Keyword.put("equals", new Standard.equal());
        Keyword.put("combine", new Standard.combine());
        Keyword.put("reduce", new Standard.reduce());
        Keyword.put("map", new Standard.map());
        Keyword.put("Array", new Standard.Array());
        Keyword.put("echo", new Standard.echo());
        Keyword.put("readln", new Standard.input());
        Keyword.put("length", new Standard.length());
        Keyword.put("getBytes", Standard.string::getBytes);
        Keyword.put("sprintf", new Standard.sprintf());
        Keyword.put("range", new Standard.range());
        Keyword.put("substring", new Standard.substr());
        Keyword.put("foreach", new Standard.foreach());
        Keyword.put("split", new Standard.split());
        Keyword.put("filter", new Standard.filter(false));

    }

    static {
        OPERATORS = new HashMap<>();

        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);
        OPERATORS.put("%", TokenType.PERCENT);
        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("[", TokenType.LBRACKET);
        OPERATORS.put("]", TokenType.RBRACKET);
        OPERATORS.put("{", TokenType.LBRACE);
        OPERATORS.put("}", TokenType.RBRACE);
        OPERATORS.put("=", TokenType.EQ);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);
        OPERATORS.put(".", TokenType.DOT);
        OPERATORS.put(",", TokenType.COMMA);
        OPERATORS.put("^", TokenType.CARET);
        OPERATORS.put("~", TokenType.TILDE);
        OPERATORS.put("?", TokenType.QUESTION);
        OPERATORS.put(":", TokenType.COLON);

        OPERATORS.put("!", TokenType.EXCL);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);

        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);

        OPERATORS.put("+=", TokenType.PLUSEQ);
        OPERATORS.put("-=", TokenType.MINUSEQ);
        OPERATORS.put("*=", TokenType.STAREQ);
        OPERATORS.put("/=", TokenType.SLASHEQ);
        OPERATORS.put("%=", TokenType.PERCENTEQ);
        OPERATORS.put("&=", TokenType.AMPEQ);
        OPERATORS.put("^=", TokenType.CARETEQ);
        OPERATORS.put("|=", TokenType.BAREQ);
        OPERATORS.put("::=", TokenType.COLONCOLONEQ);
        OPERATORS.put("<<=", TokenType.LTLTEQ);
        OPERATORS.put(">>=", TokenType.GTGTEQ);
        OPERATORS.put(">>>=", TokenType.GTGTGTEQ);

        OPERATORS.put("++", TokenType.PLUSPLUS);
        OPERATORS.put("--", TokenType.MINUSMINUS);

        OPERATORS.put("::", TokenType.COLONCOLON);

        OPERATORS.put("&&", TokenType.AMPAMP);
        OPERATORS.put("||", TokenType.BARBAR);

        OPERATORS.put("<<", TokenType.LTLT);
        OPERATORS.put(">>", TokenType.GTGT);
        OPERATORS.put(">>>", TokenType.GTGTGT);

        OPERATORS.put("@", TokenType.AT);
        OPERATORS.put("@=", TokenType.ATEQ);
        OPERATORS.put("..", TokenType.DOTDOT);
        OPERATORS.put("**", TokenType.STARSTAR);
        OPERATORS.put("^^", TokenType.CARETCARET);
        OPERATORS.put("?:", TokenType.QUESTIONCOLON);
        OPERATORS.put("??", TokenType.QUESTIONQUESTION);
    }
}
