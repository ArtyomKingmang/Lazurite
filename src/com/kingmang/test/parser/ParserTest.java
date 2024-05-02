package com.kingmang.test.parser;

import com.kingmang.lazurite.parser.AST.Expressions.BinaryExpression;
import org.junit.Test;

import static com.kingmang.test.parser.ParserHelper.*;

public class ParserTest {
    
    @Test
    public void testParsePrimary() {
        assertEval(number(2), "2", value(2));
        assertEval(string("test"), "\"test\"", value("test"));
    }
    
    @Test
    public void testParseAdditive() {
        assertEval( number(5), "2 + 3", operator(BinaryExpression.Operator.ADD, value(2), value(3)) );
        assertEval( number(-1), "2 - 3", operator(BinaryExpression.Operator.SUBTRACT, value(2), value(3)) );
    }
    
    @Test
    public void testParseMultiplicative() {
        assertEval( number(6), "2 * 3", operator(BinaryExpression.Operator.MULTIPLY, value(2), value(3)) );
        assertEval( number(4), "12 / 3", operator(BinaryExpression.Operator.DIVIDE, value(12), value(3)) );
        assertEval( number(2), "12 % 5", operator(BinaryExpression.Operator.REMAINDER, value(12), value(5)) );
    }
    

}
