package com.kingmang.lazurite.parser.parse;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.libraries.keyword;
import com.kingmang.lazurite.core.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingmang.lazurite.parser.standart.Standart;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Variables;


public final class Lexer {

    public static List<Token> tokenize(String input) {
        return new Lexer(input).tokenize();
    }
    private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:";
    private static final com.kingmang.lazurite.parser.parse.TokenType[] tokenTypes = com.kingmang.lazurite.parser.parse.TokenType.values();

    //ключевые слова
    private static final String[] keywords = {
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
            "include",
            "class",
            "new",
            "enum"
    };


    //добавление ключевых слов из массива keywords в Map KEYWORDS
    private static final Map<String, com.kingmang.lazurite.parser.parse.TokenType> KEYWORDS;
    static {
        KEYWORDS = new HashMap<>();
        for (int i = 0; i < keywords.length; i++) {
            if (i < tokenTypes.length) {
                KEYWORDS.put(keywords[i], tokenTypes[i]);
            } else {
                Console.println("Not enough token types for all tokens");
                break;
            }
        }
        types();
        convertTypes();
        standart();

    }

    public static void types() {
        LZRMap type = new LZRMap(5);
        type.set("object", LZRNumber.of(Types.OBJECT));
        type.set("number", LZRNumber.of(Types.NUMBER));
        type.set("string", LZRNumber.of(Types.STRING));
        type.set("array", LZRNumber.of(Types.ARRAY));
        type.set("map", LZRNumber.of(Types.MAP));
        type.set("function", LZRNumber.of(Types.FUNCTION));
        Variables.define("type", type);
        keyword.put("typeof", args -> LZRNumber.of(args[0].type()));
    }

    public static void convertTypes(){
        keyword.put("str", args -> new LZRString(args[0].asString()));
        keyword.put("num", args -> LZRNumber.of(args[0].asNumber()));
        keyword.put("byte", args -> LZRNumber.of((byte)args[0].asInt()));
        keyword.put("short", args -> LZRNumber.of((short)args[0].asInt()));
        keyword.put("int", args -> LZRNumber.of(args[0].asInt()));
        keyword.put("long", args -> LZRNumber.of((long)args[0].asNumber()));
        keyword.put("float", args -> LZRNumber.of((float)args[0].asNumber()));
        keyword.put("double", args -> LZRNumber.of(args[0].asNumber()));
    }

    private static void standart(){
        keyword.put("equals", new Standart.equal());
        keyword.put("Array", new Standart.Array());
        keyword.put("echo", new Standart.ECHO());
        keyword.put("readln", new Standart.INPUT());
        keyword.put("length", new Standart.LEN());
        keyword.put("getBytes", Standart.string::getBytes);
        keyword.put("sprintf", new Standart.SPRINTF());
        keyword.put("range", new Standart.range());
        keyword.put("substring", new Standart.SUBSTR());
        keyword.put("parseInt", Standart.PARSE::parseInt);
        keyword.put("parseLong", Standart.PARSE::parseLong);
        keyword.put("foreach", new Standart.FOREACH());
        keyword.put("flatmap", new Standart.FLATMAP());
        keyword.put("split", new Standart.split());
        keyword.put("filter", new Standart.FILTER(false));
    }

    public static Set<String> getKeywords() {
        return KEYWORDS.keySet();
    }

    private final String input;
    private final int length;
    
    private final List<Token> tokens;
    private final StringBuilder buffer;
    
    private int pos;
    private int row, col;

    public Lexer(String input) {
        this.input = input;
        length = input.length();
        
        tokens = new ArrayList<>();
        buffer = new StringBuilder();
        row = col = 1;
    }

    public List<Token> tokenize() {
        while (pos < length) {
            final char current = peek(0);
            if (Character.isDigit(current)) tokenizeNumber();
            else if (isLZRIdentifier(current)) tokenizeWord();
            else if (current == '`') tokenizeExtendedWord();
            else if (current == '"') tokenizeText();
            else if (current == '#') {
                next();
                tokenizeHexNumber(1);
            }
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                // whitespaces
                next();
            }
        }
        return tokens; // возвращает список из токенов
    }
    
    private void tokenizeNumber() {
        clearBuffer();
        char current = peek(0);
        if (current == '0' && (peek(1) == 'x' || (peek(1) == 'X'))) {
            next();
            next();
            tokenizeHexNumber(2);
            return;
        }
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1) throw error("Invalid float number");
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        addToken(com.kingmang.lazurite.parser.parse.TokenType.NUMBER, buffer.toString());
    }
    private void tokenizeHexNumber(int skipped) {
        clearBuffer();
        char current = peek(0);
        while (isHexNumber(current) || (current == '_')) {
            if (current != '_') {

                buffer.append(current);
            }
            current = next();
        }
        final int length = buffer.length();
        if (length > 0) {
            addToken(com.kingmang.lazurite.parser.parse.TokenType.HEX_NUMBER, buffer.toString());
        }
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
        while (true) {
            if (!isLZRIdentifierPart(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        
        final String word = buffer.toString();
        if (KEYWORDS.containsKey(word)) {
            addToken(KEYWORDS.get(word));
        } else {
            addToken(com.kingmang.lazurite.parser.parse.TokenType.WORD, word);
        }
    }

    private void tokenizeExtendedWord() {
        next();// skip `
        clearBuffer();
        char current = peek(0);
        while (true) {
            if (current == '`') break;
            if (current == '\0') throw error("Reached end of file while parsing extended word.");
            if (current == '\n' || current == '\r') throw error("Reached end of line while parsing extended word.");
            buffer.append(current);
            current = next();
        }
        next(); // skip closing `
        addToken(com.kingmang.lazurite.parser.parse.TokenType.WORD, buffer.toString());
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
        
        addToken(com.kingmang.lazurite.parser.parse.TokenType.TEXT, buffer.toString());
    }

    private static final Map<String, com.kingmang.lazurite.parser.parse.TokenType> OPERATORS;
    static {
        OPERATORS = new HashMap<>();

        OPERATORS.put("+", com.kingmang.lazurite.parser.parse.TokenType.PLUS);
        OPERATORS.put("-", com.kingmang.lazurite.parser.parse.TokenType.MINUS);
        OPERATORS.put("*", com.kingmang.lazurite.parser.parse.TokenType.STAR);
        OPERATORS.put("/", com.kingmang.lazurite.parser.parse.TokenType.SLASH);
        OPERATORS.put("%", com.kingmang.lazurite.parser.parse.TokenType.PERCENT);
        OPERATORS.put("(", com.kingmang.lazurite.parser.parse.TokenType.LPAREN);
        OPERATORS.put(")", com.kingmang.lazurite.parser.parse.TokenType.RPAREN);
        OPERATORS.put("[", com.kingmang.lazurite.parser.parse.TokenType.LBRACKET);
        OPERATORS.put("]", com.kingmang.lazurite.parser.parse.TokenType.RBRACKET);
        OPERATORS.put("{", com.kingmang.lazurite.parser.parse.TokenType.LBRACE);
        OPERATORS.put("}", com.kingmang.lazurite.parser.parse.TokenType.RBRACE);
        OPERATORS.put("=", com.kingmang.lazurite.parser.parse.TokenType.EQ);
        OPERATORS.put("<", com.kingmang.lazurite.parser.parse.TokenType.LT);
        OPERATORS.put(">", com.kingmang.lazurite.parser.parse.TokenType.GT);
        OPERATORS.put(".", com.kingmang.lazurite.parser.parse.TokenType.DOT);
        OPERATORS.put(",", com.kingmang.lazurite.parser.parse.TokenType.COMMA);
        OPERATORS.put("^", com.kingmang.lazurite.parser.parse.TokenType.CARET);
        OPERATORS.put("~", com.kingmang.lazurite.parser.parse.TokenType.TILDE);
        OPERATORS.put("?", com.kingmang.lazurite.parser.parse.TokenType.QUESTION);
        OPERATORS.put(":", com.kingmang.lazurite.parser.parse.TokenType.COLON);

        OPERATORS.put("!", com.kingmang.lazurite.parser.parse.TokenType.EXCL);
        OPERATORS.put("&", com.kingmang.lazurite.parser.parse.TokenType.AMP);
        OPERATORS.put("|", com.kingmang.lazurite.parser.parse.TokenType.BAR);

        OPERATORS.put("==", com.kingmang.lazurite.parser.parse.TokenType.EQEQ);
        OPERATORS.put("!=", com.kingmang.lazurite.parser.parse.TokenType.EXCLEQ);
        OPERATORS.put("<=", com.kingmang.lazurite.parser.parse.TokenType.LTEQ);
        OPERATORS.put(">=", com.kingmang.lazurite.parser.parse.TokenType.GTEQ);

        OPERATORS.put("+=", com.kingmang.lazurite.parser.parse.TokenType.PLUSEQ);
        OPERATORS.put("-=", com.kingmang.lazurite.parser.parse.TokenType.MINUSEQ);
        OPERATORS.put("*=", com.kingmang.lazurite.parser.parse.TokenType.STAREQ);
        OPERATORS.put("/=", com.kingmang.lazurite.parser.parse.TokenType.SLASHEQ);
        OPERATORS.put("%=", com.kingmang.lazurite.parser.parse.TokenType.PERCENTEQ);
        OPERATORS.put("&=", com.kingmang.lazurite.parser.parse.TokenType.AMPEQ);
        OPERATORS.put("^=", com.kingmang.lazurite.parser.parse.TokenType.CARETEQ);
        OPERATORS.put("|=", com.kingmang.lazurite.parser.parse.TokenType.BAREQ);
        OPERATORS.put("::=", com.kingmang.lazurite.parser.parse.TokenType.COLONCOLONEQ);
        OPERATORS.put("<<=", com.kingmang.lazurite.parser.parse.TokenType.LTLTEQ);
        OPERATORS.put(">>=", com.kingmang.lazurite.parser.parse.TokenType.GTGTEQ);
        OPERATORS.put(">>>=", com.kingmang.lazurite.parser.parse.TokenType.GTGTGTEQ);

        OPERATORS.put("++", com.kingmang.lazurite.parser.parse.TokenType.PLUSPLUS);
        OPERATORS.put("--", com.kingmang.lazurite.parser.parse.TokenType.MINUSMINUS);

        OPERATORS.put("::", com.kingmang.lazurite.parser.parse.TokenType.COLONCOLON);

        OPERATORS.put("&&", com.kingmang.lazurite.parser.parse.TokenType.AMPAMP);
        OPERATORS.put("||", com.kingmang.lazurite.parser.parse.TokenType.BARBAR);

        OPERATORS.put("<<", com.kingmang.lazurite.parser.parse.TokenType.LTLT);
        OPERATORS.put(">>", com.kingmang.lazurite.parser.parse.TokenType.GTGT);
        OPERATORS.put(">>>", com.kingmang.lazurite.parser.parse.TokenType.GTGTGT);

        OPERATORS.put("@", com.kingmang.lazurite.parser.parse.TokenType.AT);
        OPERATORS.put("@=", com.kingmang.lazurite.parser.parse.TokenType.ATEQ);
        OPERATORS.put("..", com.kingmang.lazurite.parser.parse.TokenType.DOTDOT);
        OPERATORS.put("**", com.kingmang.lazurite.parser.parse.TokenType.STARSTAR);
        OPERATORS.put("^^", com.kingmang.lazurite.parser.parse.TokenType.CARETCARET);
        OPERATORS.put("?:", com.kingmang.lazurite.parser.parse.TokenType.QUESTIONCOLON);
        OPERATORS.put("??", com.kingmang.lazurite.parser.parse.TokenType.QUESTIONQUESTION);
    }
    
    private void tokenizeComment() {
        char current = peek(0);
        while ("\r\n\0".indexOf(current) == -1) {
            current = next();
        }
     }
    
    private void tokenizeMultilineComment() {
        char current = peek(0);
        while (true) {
            if (current == '*' && peek(1) == '/') break;
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
    
    private void addToken(com.kingmang.lazurite.parser.parse.TokenType type) {
        addToken(type, "");
    }
    
    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text, row, col));
    }
    
    private LZRException error(String text) {
        return new LZRException("Lexer exeption","Lexer error");
    }
}
