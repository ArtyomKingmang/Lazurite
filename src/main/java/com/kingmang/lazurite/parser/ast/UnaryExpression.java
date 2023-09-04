package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.base.Types;
import com.kingmang.lazurite.runtime.Value;


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
    public Value eval() {
        final Value value = expr1.eval();
        switch (operation) {
            case INCREMENT_PREFIX: {
                if (expr1 instanceof Accessible) {
                    return ((Accessible) expr1).set(increment(value));
                }
                return increment(value);
            }
            case DECREMENT_PREFIX: {
                if (expr1 instanceof Accessible) {
                    return ((Accessible) expr1).set(decrement(value));
                }
                return decrement(value);
            }
            case INCREMENT_POSTFIX: {
                if (expr1 instanceof Accessible) {
                    ((Accessible) expr1).set(increment(value));
                    return value;
                }
                return increment(value);
            }
            case DECREMENT_POSTFIX: {
                if (expr1 instanceof Accessible) {
                    ((Accessible) expr1).set(decrement(value));
                    return value;
                }
                return decrement(value);
            }
            case NEGATE: return negate(value);
            case COMPLEMENT: return complement(value);
            case NOT: return not(value);
            default:
                throw new _OperExeption(operation);
        }
    }
    
    private Value increment(Value value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return NumberValue.of(number.doubleValue() + 1);
            }
            if (number instanceof Float) {
                return NumberValue.of(number.floatValue() + 1);
            }
            if (number instanceof Long) {
                return NumberValue.of(number.longValue() + 1);
            }
        }
        return NumberValue.of(value.asInt() + 1);
    }
    
    private Value decrement(Value value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return NumberValue.of(number.doubleValue() - 1);
            }
            if (number instanceof Float) {
                return NumberValue.of(number.floatValue() - 1);
            }
            if (number instanceof Long) {
                return NumberValue.of(number.longValue() - 1);
            }
        }
        return NumberValue.of(value.asInt() - 1);
    }
    
    private Value negate(Value value) {
        if (value.type() == Types.STRING) {
            final StringBuilder sb = new StringBuilder(value.asString());
            return new StringValue(sb.reverse().toString());
        }
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Double) {
                return NumberValue.of(-number.doubleValue());
            }
            if (number instanceof Float) {
                return NumberValue.of(-number.floatValue());
            }
            if (number instanceof Long) {
                return NumberValue.of(-number.longValue());
            }
        }
        return NumberValue.of(-value.asInt());
    }
    
    private Value complement(Value value) {
        if (value.type() == Types.NUMBER) {
            final Number number = (Number) value.raw();
            if (number instanceof Long) {
                return NumberValue.of(~number.longValue());
            }
        }
        return NumberValue.of(~value.asInt());
    }
    
    private Value not(Value value) {
        return NumberValue.fromBoolean(value.asInt() == 0);
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
