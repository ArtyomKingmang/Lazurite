package parser;

import com.kingmang.lazurite.parser.AST.Expressions.BinaryExpression;
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {


    private static void testStatement(BlockStatement expected, BlockStatement actual) {
        final int size = expected.statements.size();
        for (int i = 0; i < size; i++) {
            assertEquals(expected.statements.get(i).getClass(), actual.statements.get(i).getClass());
        }
    }

    @Test
    public void testParsePrimary() {
        ParserHelper.assertEval(ParserHelper.number(2), "2", ParserHelper.value(2));
        ParserHelper.assertEval(ParserHelper.string("test"), "\"test\"", ParserHelper.value("test"));
    }
    
    @Test
    public void testParseAdditive() {
        ParserHelper.assertEval( ParserHelper.number(5), "2 + 3", ParserHelper.operator(BinaryExpression.Operator.ADD, ParserHelper.value(2), ParserHelper.value(3)) );
        ParserHelper.assertEval( ParserHelper.number(-1), "2 - 3", ParserHelper.operator(BinaryExpression.Operator.SUBTRACT, ParserHelper.value(2), ParserHelper.value(3)) );
    }
    
    @Test
    public void testParseMultiplicative() {
        ParserHelper.assertEval( ParserHelper.number(6), "2 * 3", ParserHelper.operator(BinaryExpression.Operator.MULTIPLY, ParserHelper.value(2), ParserHelper.value(3)) );
        ParserHelper.assertEval( ParserHelper.number(4), "12 / 3", ParserHelper.operator(BinaryExpression.Operator.DIVIDE, ParserHelper.value(12), ParserHelper.value(3)) );
        ParserHelper.assertEval( ParserHelper.number(2), "12 % 5", ParserHelper.operator(BinaryExpression.Operator.REMAINDER, ParserHelper.value(12), ParserHelper.value(5)) );
    }
    

}
