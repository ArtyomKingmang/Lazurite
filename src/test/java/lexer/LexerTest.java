package lexer;

import com.kingmang.lazurite.parser.Token;
import com.kingmang.lazurite.parser.lexer.impl.LexerImplementation;
import org.junit.Test;

import java.util.List;

import static com.kingmang.lazurite.parser.TokenType.*;
import static lexer.Helper.assertTokens;
import static lexer.Helper.list;
import static org.junit.Assert.assertEquals;


public class LexerTest {

    @Test
    public void testLexerStringTemplate() {
        String input = """
                a = 2
                print ("a = $a = ${a + 4}")
                """;
        List<Token> expList = list(
                WORD, EQ, NUMBER,
                PRINT, LPAREN, TEXT, PLUS, WORD, PLUS, TEXT, PLUS, WORD, RPAREN
        );
        List<Token> result = LexerImplementation.tokenize(input);
        assertTokens(expList, result);

        input = """
                a = 14
                b = 88
                c = "ab = $a$b"
                print("$c = Procc")
                """;

        expList = list(
                WORD, EQ, NUMBER,
                WORD, EQ, NUMBER,
                WORD, EQ, TEXT, PLUS, WORD, PLUS, WORD,
                PRINT, LPAREN, WORD, PLUS, TEXT, RPAREN
        );
        result = LexerImplementation.tokenize(input);
        assertTokens(expList, result);
    }

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