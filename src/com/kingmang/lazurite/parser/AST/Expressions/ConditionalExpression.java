package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.OperationException;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;


public final class ConditionalExpression implements Expression {

    public enum Operator {
        EQUALS("=="),
        NOT_EQUALS("!="),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||"),

        NULL_COALESCE("??");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public final Expression expr1, expr2;
    public final Operator operation;

    public ConditionalExpression(Operator operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        switch (operation) {
            case AND:
                return LZRNumber.fromBoolean((expr1AsInt() != 0) && (expr2AsInt() != 0));
            case OR:
                return LZRNumber.fromBoolean((expr1AsInt() != 0) || (expr2AsInt() != 0));

            case NULL_COALESCE:
                return nullCoalesce();

            default:
                return LZRNumber.fromBoolean(evalAndCompare());
        }
    }

    private boolean evalAndCompare() {
        final Value value1 = expr1.eval();
        final Value value2 = expr2.eval();

        double number1, number2;
        if (value1.type() == Types.NUMBER) {
            number1 = value1.asNumber();
            number2 = value2.asNumber();
        } else {
            number1 = value1.compareTo(value2);
            number2 = 0;
        }

        switch (operation) {
            case EQUALS: return number1 == number2;
            case NOT_EQUALS: return number1 != number2;

            case LT: return number1 < number2;
            case LTEQ: return number1 <= number2;
            case GT: return number1 > number2;
            case GTEQ: return number1 >= number2;

            default:
                throw new OperationException(operation);
        }
    }

    private Value nullCoalesce() {
        Value value1;
        try {
            value1 = expr1.eval();
        } catch (NullPointerException npe) {
            value1 = null;
        }
        if (value1 == null) {
            return expr2.eval();
        }
        return value1;
    }

    private int expr1AsInt() {
        return expr1.eval().asInt();
    }

    private int expr2AsInt() {
        return expr2.eval().asInt();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", expr1, operation.getName(), expr2);
    }
}
