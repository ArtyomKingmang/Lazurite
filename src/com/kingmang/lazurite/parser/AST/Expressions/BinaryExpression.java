package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.exceptions.OperationException;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.libraries.keyword;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;


public final class BinaryExpression implements Expression {
    
    public enum Operator {
        PUSH("::"),
        AND("&"),
        OR("|"),
        XOR("^"),
        LSHIFT("<<"),
        RSHIFT(">>"),
        URSHIFT(">>>"),
        AT("@"),
        CARETCARET("^^"),
        RANGE(".."),
        POWER("**"),
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        REMAINDER("%"),

        ELVIS("?:");
        
        private final String name;

        Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    public final Operator operation;
    public final Expression expr1, expr2;

    public BinaryExpression(Operator operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        final Value value1 = expr1.eval();
        final Value value2 = expr2.eval();
        try {
            return eval(value1, value2);
        } catch (OperationException ex) {
            if (keyword.isExists(operation.toString())) {
                return keyword.get(operation.toString()).execute(value1, value2);
            }
            throw ex;
        }
    }
    
    private Value eval(Value value1, Value value2) {
        switch (operation) {
            case ADD: return add(value1, value2);
            case SUBTRACT: return subtract(value1, value2);
            case MULTIPLY: return multiply(value1, value2);
            case DIVIDE: return divide(value1, value2);
            case REMAINDER: return remainder(value1, value2);
            case PUSH: return push(value1, value2);
            case AND: return and(value1, value2);
            case OR: return or(value1, value2);
            case XOR: return xor(value1, value2);
            case LSHIFT: return lshift(value1, value2);
            case RSHIFT: return rshift(value1, value2);
            case URSHIFT: return urshift(value1, value2);
            default:
                throw new OperationException(operation);
        }
    }
    
    private Value add(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return add((LZRNumber) value1, value2);
            case Types.ARRAY: return LZRArray.add((LZRArray) value1, value2);
            case Types.MAP:
                if (value2.type() != Types.MAP)
                    throw new LZRException("TypeExeption","Cannot merge non map value to map");
                return LZRMap.merge((LZRMap) value1, (LZRMap) value2);
            case Types.FUNCTION: /* TODO: combining functions */
            case Types.STRING:
            default:
                // Concatenation strings
                return new LZRString(value1.asString() + value2.asString());
        }
    }
    
    private Value add(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 + number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LZRNumber.of(number1.doubleValue() + number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LZRNumber.of(number1.floatValue() + number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() + number2.longValue());
            }
            return LZRNumber.of(number1.intValue() + number2.intValue());
        }
        // number1 + other
        if (number1 instanceof Double) {
            return LZRNumber.of(number1.doubleValue() + value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LZRNumber.of(number1.floatValue() + value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() + value2.asInt());
        }
        return LZRNumber.of(number1.intValue() + value2.asInt());
    }
    
    private Value subtract(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return subtract((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value subtract(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 - number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LZRNumber.of(number1.doubleValue() - number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LZRNumber.of(number1.floatValue() - number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() - number2.longValue());
            }
            return LZRNumber.of(number1.intValue() - number2.intValue());
        }
        // number1 - other
        if (number1 instanceof Double) {
            return LZRNumber.of(number1.doubleValue() - value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LZRNumber.of(number1.floatValue() - value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() - value2.asInt());
        }
        return LZRNumber.of(number1.intValue() - value2.asInt());
    }
    
    private Value multiply(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return multiply((LZRNumber) value1, value2);
            case Types.STRING: return multiply((LZRString) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value multiply(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 * number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LZRNumber.of(number1.doubleValue() * number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LZRNumber.of(number1.floatValue() * number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() * number2.longValue());
            }
            return LZRNumber.of(number1.intValue() * number2.intValue());
        }
        // number1 * other
        if (number1 instanceof Double) {
            return LZRNumber.of(number1.doubleValue() * value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LZRNumber.of(number1.floatValue() * value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() * value2.asInt());
        }
        return LZRNumber.of(number1.intValue() * value2.asInt());
    }

    private Value multiply(LZRString value1, Value value2) {
        final String string1 = value1.asString();
        final int iterations = value2.asInt();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            buffer.append(string1);
        }
        return new LZRString(buffer.toString());
    }
    
    private Value divide(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return divide((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value divide(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 / number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LZRNumber.of(number1.doubleValue() / number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LZRNumber.of(number1.floatValue() / number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() / number2.longValue());
            }
            return LZRNumber.of(number1.intValue() / number2.intValue());
        }
        // number1 / other
        if (number1 instanceof Double) {
            return LZRNumber.of(number1.doubleValue() / value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LZRNumber.of(number1.floatValue() / value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() / value2.asInt());
        }
        return LZRNumber.of(number1.intValue() / value2.asInt());
    }
    
    private Value remainder(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return remainder((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value remainder(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 % number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LZRNumber.of(number1.doubleValue() % number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LZRNumber.of(number1.floatValue() % number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() % number2.longValue());
            }
            return LZRNumber.of(number1.intValue() % number2.intValue());
        }
        // number1 % other
        if (number1 instanceof Double) {
            return LZRNumber.of(number1.doubleValue() % value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LZRNumber.of(number1.floatValue() % value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() % value2.asInt());
        }
        return LZRNumber.of(number1.intValue() % value2.asInt());
    }
    
    private Value push(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.ARRAY: return LZRArray.add((LZRArray) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }

    private Value and(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return and((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value and(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 & number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() & number2.longValue());
            }
            return LZRNumber.of(number1.intValue() & number2.intValue());
        }
        // number1 & other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() & value2.asInt());
        }
        return LZRNumber.of(number1.intValue() & value2.asInt());
    }
    
    private Value or(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return or((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value or(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 | number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() | number2.longValue());
            }
            return LZRNumber.of(number1.intValue() | number2.intValue());
        }
        // number1 | other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() | value2.asInt());
        }
        return LZRNumber.of(number1.intValue() | value2.asInt());
    }
    
    private Value xor(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return xor((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value xor(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 ^ number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() ^ number2.longValue());
            }
            return LZRNumber.of(number1.intValue() ^ number2.intValue());
        }
        // number1 ^ other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() ^ value2.asInt());
        }
        return LZRNumber.of(number1.intValue() ^ value2.asInt());
    }
    
    private Value lshift(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return lshift((LZRNumber) value1, value2);
            case Types.ARRAY: return lshift((LZRArray) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }

    private Value lshift(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 << number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() << number2.longValue());
            }
            return LZRNumber.of(number1.intValue() << number2.intValue());
        }
        // number1 << other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() << value2.asInt());
        }
        return LZRNumber.of(number1.intValue() << value2.asInt());
    }

    private Value lshift(LZRArray value1, Value value2) {
        if (value2.type() != Types.ARRAY)
            throw new LZRException("TypeExeption", "Cannot merge non array value to array");
        return LZRArray.merge(value1, (LZRArray) value2);
    }
    
    private Value rshift(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return rshift((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value rshift(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 >> number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() >> number2.longValue());
            }
            return LZRNumber.of(number1.intValue() >> number2.intValue());
        }
        // number1 >> other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() >> value2.asInt());
        }
        return LZRNumber.of(number1.intValue() >> value2.asInt());
    }
    
    private Value urshift(Value value1, Value value2) {
        switch (value1.type()) {
            case Types.NUMBER: return urshift((LZRNumber) value1, value2);
            default:
                throw new OperationException(operation,
                        "for " + Types.typeToString(value1.type()));
        }
    }
    
    private Value urshift(LZRNumber value1, Value value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 >>> number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LZRNumber.of(number1.longValue() >>> number2.longValue());
            }
            return LZRNumber.of(number1.intValue() >>> number2.intValue());
        }
        // number1 >>> other
        if (number1 instanceof Long) {
            return LZRNumber.of(number1.longValue() >>> value2.asInt());
        }
        return LZRNumber.of(number1.intValue() >>> value2.asInt());
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
        return String.format("%s %s %s", expr1, operation, expr2);
    }
}
