package lexer;

import com.kingmang.lazurite.parser.tokens.Token;
import com.kingmang.lazurite.parser.tokens.TokenType;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    protected static void assertTokens(List<Token> expList, List<Token> result) {
        final int length = expList.size();
        Assertions.assertEquals(length, result.size());
        for (int i = 0; i < length; i++) {
            Assertions.assertEquals(expList.get(i).getType(), result.get(i).getType());
        }
    }

    protected static List<Token> list(TokenType... types) {
        final List<Token> list = new ArrayList<>();
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
