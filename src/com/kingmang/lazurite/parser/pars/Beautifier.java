package com.kingmang.lazurite.parser.pars;

import java.util.HashMap;
import java.util.Map;


public final class Beautifier {
    
    public static String beautify(String input) {
        return new Beautifier(input).beautify();
    }

    private enum OperatorMode {
        SPACES, RSPACES, TRIM, RTRIM, AS_SOURCE,
    }

    private static final String OPERATOR_CHARS = "+-*/%()[]=<>!&|.,^~?:";
    
    private static final Map<String, OperatorMode> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", OperatorMode.SPACES);
        OPERATORS.put("-", OperatorMode.SPACES);
        OPERATORS.put("*", OperatorMode.SPACES);
        OPERATORS.put("/", OperatorMode.SPACES);
        OPERATORS.put("%", OperatorMode.SPACES);
        OPERATORS.put("(", OperatorMode.AS_SOURCE);
        OPERATORS.put(")", OperatorMode.AS_SOURCE);
        OPERATORS.put("[", OperatorMode.AS_SOURCE);
        OPERATORS.put("]", OperatorMode.AS_SOURCE);
        OPERATORS.put("=", OperatorMode.SPACES);
        OPERATORS.put("<", OperatorMode.SPACES);
        OPERATORS.put(">", OperatorMode.SPACES);
        OPERATORS.put(".", OperatorMode.TRIM);
        OPERATORS.put(",", OperatorMode.RSPACES);
        OPERATORS.put("^", OperatorMode.SPACES);
        OPERATORS.put("~", OperatorMode.RTRIM);
        OPERATORS.put("?", OperatorMode.SPACES);
        OPERATORS.put(":", OperatorMode.SPACES);
        
        OPERATORS.put("!", OperatorMode.RTRIM);
        OPERATORS.put("&", OperatorMode.SPACES);
        OPERATORS.put("|", OperatorMode.SPACES);
        
        OPERATORS.put("==", OperatorMode.SPACES);
        OPERATORS.put("!=", OperatorMode.SPACES);
        OPERATORS.put("<=", OperatorMode.SPACES);
        OPERATORS.put(">=", OperatorMode.SPACES);
        
        OPERATORS.put("+=", OperatorMode.SPACES);
        OPERATORS.put("-=", OperatorMode.SPACES);
        OPERATORS.put("*=", OperatorMode.SPACES);
        OPERATORS.put("/=", OperatorMode.SPACES);
        OPERATORS.put("%=", OperatorMode.SPACES);
        OPERATORS.put("&=", OperatorMode.SPACES);
        OPERATORS.put("^=", OperatorMode.SPACES);
        OPERATORS.put("|=", OperatorMode.SPACES);
        OPERATORS.put("::=", OperatorMode.SPACES);
        OPERATORS.put("<<=", OperatorMode.SPACES);
        OPERATORS.put(">>=", OperatorMode.SPACES);
        OPERATORS.put(">>>=", OperatorMode.SPACES);

        OPERATORS.put("++", OperatorMode.AS_SOURCE);
        OPERATORS.put("--", OperatorMode.AS_SOURCE);
        
        OPERATORS.put("::", OperatorMode.AS_SOURCE);
        
        OPERATORS.put("&&", OperatorMode.SPACES);
        OPERATORS.put("||", OperatorMode.SPACES);
        
        OPERATORS.put("<<", OperatorMode.SPACES);
        OPERATORS.put(">>", OperatorMode.SPACES);
        OPERATORS.put(">>>", OperatorMode.SPACES);
    }
    
    private final String input;
    private final int length;
    
    private final StringBuilder beautifiedCode, buffer;
    
    private int pos;
    private int indentLevel;

    public Beautifier(String input) {
        this.input = input;
        length = input.length();
        beautifiedCode = new StringBuilder();
        buffer = new StringBuilder();

        indentLevel = 0;
    }
    
    public String beautify() {
        while (pos < length) {
            final char current = peek(0);
            if (current == '"') processText();
            else if (current == '`') processExtendedWord();
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                processOperator();
            } else switch (current) {
                case '{':
                    indentLevel += 2;
                    addSpaceToLeft();
                    beautifiedCode.append(current);
                    newLineStrict();
                    break;
                case '}':
                    indentLevel -= 2;
                    trimLeft();
                    addToLeft('\n');
                    indent();
                    beautifiedCode.append(current);
                    newLineStrict();
                    break;
                case '\n':
                    newLineStrict();
                    break;
                default:
                    beautifiedCode.append(current);
                    next();
                    break;
            }
        }
        return beautifiedCode.toString();
    }

    private void processText() {
        int index = pos + 1;
        while ((index = 1 + input.indexOf('"', index)) != -1) {
            if (input.charAt(index - 1) != '\\') {
                skipTo(index);
                return;
            }
        }
    }

    private void processExtendedWord() {
        skipTo("`");
    }

    private void processComment() {
        skipTo("\n");
    }

    private void processMultilineComment() {
        skipTo("*/");
        newLineStrict();
        pos--;
    }

    private void processOperator() {
        char current = peek(0);
        if (current == '/') {
            if (peek(1) == '/') {
                processComment();
                return;
            } else if (peek(1) == '*') {
                processMultilineComment();
                return;
            }
        }
        clearBuffer();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                final OperatorMode mode = OPERATORS.get(text);
                switch (mode) {
                    case SPACES:
                        addSpaceToLeft();
                        beautifiedCode.append(buffer);
                        addSpaceToRight();
                        break;
                    case RSPACES:
                        beautifiedCode.append(buffer);
                        addSpaceToRight();
                        break;

                    case AS_SOURCE:
                        beautifiedCode.append(buffer);
                        break;

                    case RTRIM:
                        beautifiedCode.append(buffer);
                        trimRight();
                        break;
                    case TRIM:
                        trimLeft();
                        beautifiedCode.append(buffer);
                        trimRight();
                        break;
                }
                return;
            }
            buffer.append(current);
            current = next();
        }
    }


    private void trimLeft() {
        while (isSpace(last())) {
            beautifiedCode.setLength(beautifiedCode.length() - 1);
        }
    }

    private void trimRight() {
        while (isSpace(peek(0))) {
            next();
        }
    }

    private void addSpaceToLeft() {
        addToLeft(' ');
    }

    private void addSpaceToRight() {
        if (peek(0) != ' ') {
            beautifiedCode.append(' ');
        }
    }

    private void addToLeft(char ch) {
        if (last() != ch) {
            beautifiedCode.append(ch);
        }
    }

    private boolean isSpace(char ch) {
        return " ".indexOf(ch) != -1;
    }

    private void newLineStrict() {
        beautifiedCode.append(Console.newline());
        indent();
        do {
            next();
        } while (Character.isWhitespace(peek(0)));
    }

    private void indent() {
        indent(indentLevel);
    }

    private void indent(int count) {
        for (int i = 0; i < count; i++) {
            beautifiedCode.append(' ');
        }
    }

    private void skipTo(String text) {
        int index = input.indexOf(text, pos);
        if (index == -1) {
            index = length - 1;
        } else {
            index += text.length();
        }
        skipTo(index);
    }

    private void skipTo(int position) {
        beautifiedCode.append(input.substring(pos, position));
        pos += (position - pos);
    }

    private char last() {
        return beautifiedCode.charAt(beautifiedCode.length() - 1);
    }

    private void clearBuffer() {
        buffer.setLength(0);
    }

    private char next() {
        pos++;
        return peek(0);
    }
    
    private char peek(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return input.charAt(position);
    }
}
