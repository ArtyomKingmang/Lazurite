package com.kingmang.test.lexer;

import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.parser.parse.TokenType;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import org.junit.Test;

import java.util.List;

import static com.kingmang.lazurite.parser.parse.TokenType.*;
import static com.kingmang.test.lexer.Helper.assertTokens;
import static com.kingmang.test.lexer.Helper.list;

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


    
}