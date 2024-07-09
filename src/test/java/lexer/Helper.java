package lexer;

import com.kingmang.lazurite.parser.Token;
import com.kingmang.lazurite.parser.TokenType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Helper {

    protected static void assertTokens(List<Token> expList, List<Token> result) {
        final int length = expList.size();
        assertEquals(length, result.size());
        for (int i = 0; i < length; i++) {
            assertEquals(expList.get(i).getType(), result.get(i).getType());
        }
    }

    protected static List<Token> list(TokenType... types) {
        final List<Token> list = new ArrayList<Token>();
        for (TokenType t : types) {
            list.add(token(t));
        }
        return list;
    }

    protected static Token token(TokenType type) {
        return token(type, "", 0, 0);
    }

    protected static Token token(TokenType type, String text, int row, int col) {
        return new Token(type, text, row, col);
    }
}
