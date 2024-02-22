package com.kingmang.test.lexer;

import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.parser.parse.TokenType;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import org.junit.Test;

import java.util.List;

import static com.kingmang.lazurite.parser.parse.TokenType.*;
import static com.kingmang.test.lexer.Helper.assertTokens;
import static com.kingmang.test.lexer.Helper.list;
import static junit.framework.TestCase.assertEquals;

public class LexerTest {

    @Test
    public void testLexer() {
        String input = """
                print  ("Hello")
                """;
        List<Token> expList = list(PRINT, LPAREN, TEXT, RPAREN);
        List<Token> result = LexerImplementation.tokenize(input);
        assertTokens(expList, result);
    }

    @Test
    public void testOperators() {
        String input = "=+-*/%<>!&|";
        List<Token> expList = list(EQ, PLUS, MINUS, STAR, SLASH, PERCENT, LT, GT, EXCL, AMP, BAR);
        List<Token> result = LexerImplementation.tokenize(input);
        assertTokens(expList, result);
    }

    @Test
    public void testString() {
        String input = "\"1\\\"2\"";
        List<Token> expList = list(TEXT);
        List<Token> result = LexerImplementation.tokenize(input);
        assertTokens(expList, result);
        assertEquals("1\"2", result.get(0).getText());
    }
    
}