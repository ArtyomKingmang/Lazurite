package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.parser.AST.Accessible;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.runtime.Types.LzrString;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.LzrValue;


public final class UnaryExpression implements Expression, Statement {

    public enum Operator {
        INCREMENT_PREFIX("++"),
        DECREMENT_PREFIX("--"),
        INCREMENT_POSTFIX("++"),
        DECREMENT_POSTFIX("--"),
        NEGATE("-"),
        // Boolean
        NOT("!"),
        // Bitwise
        COMPLEMENT("~");
        
        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    public final Expression expr1;
    public final Operator operation;

    public UnaryExpression(Operator operation, Expression expr1) {
        this.operation = operation;
        this.expr1 = expr1;
    }
    
    @Override
    public void execute() {
        eval();
    }
    
    @Override
    public LzrValue eval() {
        final LzrValue value = expr1.eval();
        switch (operation) {
            case INCREMENT_PREFIX: {
                if (expr1 instanceof com.kingmang.lazurite.parser.AST.Accessible) {
                    return ((com.kingmang.lazurite.parser.AST.Accessible) expr1).set(increment(value));
                }
                return increment(value);
            }
            case DECREMENT_PREFIX: {
                if (expr1 instanceof com.kingmang.lazurite.parser.AST.Accessible) {
                    return ((com.kingmang.lazurite.parser.AST.Accessible) expr1).set(decrement(value));
                }
                return decrement(value);
            }
            case INCREMENT_POSTFIX: {
                if (expr1 instanceof com.kingmang.lazurite.parser.AST.Accessible) {
                    ((com.kingmang.lazurite.parser.AST.Accessible) expr1).set(increment(value));
                    return value;
                }
                return increment(value);
            }
            case DECREMENT_POSTFIX: {
                if (expr1 instanceof com.kingmang.lazurite.parser.AST.Accessible) {
                    ((Accessible) expr1).set(decrement(value));
                    return value;
                }
                return decrement(value);
            }
            case NEGATE: return negate(value);
            case COMPLEMENT: return complement(value);
            case NOT: return not(value);
            default:
                throw new LZRException("OperationIsNotSupportedException ","Operation " + operation + " is not supported");
        }
    }
    
    private LzrValue increment(LzrValue value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return LzrNumber.of(number.doubleValue() + 1);
            }
            if (number instanceof Float) {
                return LzrNumber.of(number.floatValue() + 1);
            }
            if (number instanceof Long) {
                return LzrNumber.of(number.longValue() + 1);
            }
        }
        return LzrNumber.of(value.asInt() + 1);
    }
    
    private LzrValue decrement(LzrValue value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return LzrNumber.of(number.doubleValue() - 1);
            }
            if (number instanceof Float) {
                return LzrNumber.of(number.floatValue() - 1);
            }
            if (number instanceof Long) {
                return LzrNumber.of(number.longValue() - 1);
            }
        }
        return LzrNumber.of(value.asInt() - 1);
    }
    
    private LzrValue negate(LzrValue value) {
        if (value.type() == Types.STRING) {
            final StringBuilder sb = new StringBuilder(value.asString());
            return new LzrString(sb.reverse().toString());
        }
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return LzrNumber.of(-number.doubleValue());
            }
            if (number instanceof Float) {
                return LzrNumber.of(-number.floatValue());
            }
            if (number instanceof Long) {
                return LzrNumber.of(-number.longValue());
            }
        }
        return LzrNumber.of(-value.asInt());
    }
    
    private LzrValue complement(LzrValue value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Long) {
                return LzrNumber.of(~number.longValue());
            }
        }
        return LzrNumber.of(~value.asInt());
    }
    
    private LzrValue not(LzrValue value) {
        return LzrNumber.fromBoolean(value.asInt() == 0);
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
        switch (operation) {
            case INCREMENT_POSTFIX:
            case DECREMENT_POSTFIX:
                return String.format("%s %s", expr1, operation);
            default:
                return String.format("%s %s", operation, expr1);
        }
    }
}
