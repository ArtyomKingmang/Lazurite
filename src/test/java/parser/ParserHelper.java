package parser;

import com.kingmang.lazurite.parser.AST.Accessible;
import com.kingmang.lazurite.parser.AST.Expressions.*;
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.parser.parse.impl.LexerImplementation;
import com.kingmang.lazurite.parser.parse.impl.ParserImplementation;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.Variables;

import static junit.framework.TestCase.assertEquals;

public final class ParserHelper {
    
    public static BlockStatement block(Statement... statements) {
        final BlockStatement result = new BlockStatement();
        for (Statement statement : statements) {
            result.add(statement);
        }
        return result;
    }
    public static void assertEval(LzrValue expectedValue, String input, Expression expected) {
        BlockStatement program = assertExpression(input, expected);
        program.execute();
        final LzrValue actual = Variables.get("a");
        try {
            assertEquals(expectedValue.asNumber(), actual.asNumber(), 0.001);
        } catch (NumberFormatException nfe) {
            assertEquals(expectedValue.asString(), actual.asString());
        }
    }

    private static BlockStatement assertExpression(String input, Expression expected) {
        return assertProgram("a = " + input, block(assign("a", expected)));
    }

    private static BlockStatement assertProgram(String input, BlockStatement actual) {
        BlockStatement result = (BlockStatement) parse(input);
        assertStatements(result, actual);
        return result;
    }

    private static void assertStatements(BlockStatement expected, BlockStatement actual) {
        final int size = expected.statements.size();
        for (int i = 0; i < size; i++) {
            assertEquals(expected.statements.get(i).getClass(), actual.statements.get(i).getClass());
        }
    }

    private static Statement parse(String input) {
        ParserImplementation parserImplementation = new ParserImplementation(LexerImplementation.tokenize(input));
        return parserImplementation.parse();
    }
    
    public static AssignmentExpression assign(String variable, Expression expr) {
        return assign(var(variable), expr);
    }
    
    public static AssignmentExpression assign(Accessible accessible, Expression expr) {
        return assign(null, accessible, expr);
    }
    
    public static AssignmentExpression assign(BinaryExpression.Operator op, Accessible accessible, Expression expr) {
        return new AssignmentExpression(op, accessible, expr);
    }
    
    public static BinaryExpression operator(BinaryExpression.Operator op, Expression left, Expression right) {
        return new BinaryExpression(op, left, right);
    }
    
    public static ValueExpression value(Number value) {
        return new ValueExpression(value);
    }
    
    public static ValueExpression value(String value) {
        return new ValueExpression(value);
    }
    
    public static VariableExpression var(String value) {
        return new VariableExpression(value);
    }
    
    
    public static LzrNumber number(Number value) {
        return LzrNumber.of(value);
    }
    
    public static LzrString string(String value) {
        return new LzrString(value);
    }
}
