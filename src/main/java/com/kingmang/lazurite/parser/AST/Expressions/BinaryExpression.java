package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.runtime.Types.LzrMap;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.runtime.Types.LzrString;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.LzrValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
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


    @Override
    public LzrValue eval() {
        final LzrValue value1 = expr1.eval();
        final LzrValue value2 = expr2.eval();
        try {
            return eval(value1, value2);
        } catch (OperationIsNotSupportedException ex) {
            if (Keyword.isExists(operation.toString())) {
                return Keyword.get(operation.toString()).execute(value1, value2);
            }
            throw ex;
        }
    }
    
    private LzrValue eval(LzrValue value1, LzrValue value2) {
        return switch (operation) {
            case ADD -> add(value1, value2);
            case SUBTRACT -> subtract(value1, value2);
            case MULTIPLY -> multiply(value1, value2);
            case DIVIDE -> divide(value1, value2);
            case REMAINDER -> remainder(value1, value2);
            case PUSH -> push(value1, value2);
            case AND -> and(value1, value2);
            case OR -> or(value1, value2);
            case XOR -> xor(value1, value2);
            case LSHIFT -> lshift(value1, value2);
            case RSHIFT -> rshift(value1, value2);
            case URSHIFT -> urshift(value1, value2);
            default -> throw new OperationIsNotSupportedException(operation);
        };
    }
    
    private LzrValue add(LzrValue value1, LzrValue value2) {
        return switch (value1.type()) {
            case Types.NUMBER -> add((LzrNumber) value1, value2);
            case Types.ARRAY -> LzrArray.add((LzrArray) value1, value2);
            case Types.MAP -> {
                if (value2.type() != Types.MAP)
                    throw new LZRException("TypeException", "Cannot merge non map value to map");
                yield LzrMap.merge((LzrMap) value1, (LzrMap) value2);
            } /* TODO: combining functions */
            default ->
                // Concatenation strings
                    new LzrString(value1.asString() + value2.asString());
        };
    }
    
    private LzrValue add(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 + number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LzrNumber.of(number1.doubleValue() + number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LzrNumber.of(number1.floatValue() + number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() + number2.longValue());
            }
            return LzrNumber.of(number1.intValue() + number2.intValue());
        }
        // number1 + other
        if (number1 instanceof Double) {
            return LzrNumber.of(number1.doubleValue() + value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LzrNumber.of(number1.floatValue() + value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() + value2.asInt());
        }
        return LzrNumber.of(number1.intValue() + value2.asInt());
    }
    
    private LzrValue subtract(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return subtract((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue subtract(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 - number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LzrNumber.of(number1.doubleValue() - number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LzrNumber.of(number1.floatValue() - number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() - number2.longValue());
            }
            return LzrNumber.of(number1.intValue() - number2.intValue());
        }
        // number1 - other
        if (number1 instanceof Double) {
            return LzrNumber.of(number1.doubleValue() - value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LzrNumber.of(number1.floatValue() - value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() - value2.asInt());
        }
        return LzrNumber.of(number1.intValue() - value2.asInt());
    }
    
    private LzrValue multiply(LzrValue value1, LzrValue value2) {
        return switch (value1.type()) {
            case Types.NUMBER -> multiply((LzrNumber) value1, value2);
            case Types.STRING -> multiply((LzrString) value1, value2);
            default -> throw new OperationIsNotSupportedException(operation,
                    "for " + Types.typeToString(value1.type()));
        };
    }
    
    private LzrValue multiply(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 * number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LzrNumber.of(number1.doubleValue() * number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LzrNumber.of(number1.floatValue() * number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() * number2.longValue());
            }
            return LzrNumber.of(number1.intValue() * number2.intValue());
        }
        // number1 * other
        if (number1 instanceof Double) {
            return LzrNumber.of(number1.doubleValue() * value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LzrNumber.of(number1.floatValue() * value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() * value2.asInt());
        }
        return LzrNumber.of(number1.intValue() * value2.asInt());
    }

    private LzrValue multiply(LzrString value1, LzrValue value2) {
        final String string1 = value1.asString();
        final int iterations = value2.asInt();
        return new LzrString(String.valueOf(string1).repeat(Math.max(0, iterations)));
    }
    
    private LzrValue divide(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return divide((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue divide(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 / number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LzrNumber.of(number1.doubleValue() / number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LzrNumber.of(number1.floatValue() / number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() / number2.longValue());
            }
            return LzrNumber.of(number1.intValue() / number2.intValue());
        }
        // number1 / other
        if (number1 instanceof Double) {
            return LzrNumber.of(number1.doubleValue() / value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LzrNumber.of(number1.floatValue() / value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() / value2.asInt());
        }
        return LzrNumber.of(number1.intValue() / value2.asInt());
    }
    
    private LzrValue remainder(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return remainder((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue remainder(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 % number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Double || number2 instanceof Double) {
                return LzrNumber.of(number1.doubleValue() % number2.doubleValue());
            }
            if (number1 instanceof Float || number2 instanceof Float) {
                return LzrNumber.of(number1.floatValue() % number2.floatValue());
            }
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() % number2.longValue());
            }
            return LzrNumber.of(number1.intValue() % number2.intValue());
        }
        // number1 % other
        if (number1 instanceof Double) {
            return LzrNumber.of(number1.doubleValue() % value2.asNumber());
        }
        if (number1 instanceof Float) {
            return LzrNumber.of(number1.floatValue() % value2.asNumber());
        }
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() % value2.asInt());
        }
        return LzrNumber.of(number1.intValue() % value2.asInt());
    }
    
    private LzrValue push(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.ARRAY) {
            return LzrArray.add((LzrArray) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }

    private LzrValue and(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return and((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue and(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 & number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() & number2.longValue());
            }
            return LzrNumber.of(number1.intValue() & number2.intValue());
        }
        // number1 & other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() & value2.asInt());
        }
        return LzrNumber.of(number1.intValue() & value2.asInt());
    }
    
    private LzrValue or(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return or((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue or(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 | number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() | number2.longValue());
            }
            return LzrNumber.of(number1.intValue() | number2.intValue());
        }
        // number1 | other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() | value2.asInt());
        }
        return LzrNumber.of(number1.intValue() | value2.asInt());
    }
    
    private LzrValue xor(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return xor((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue xor(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 ^ number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() ^ number2.longValue());
            }
            return LzrNumber.of(number1.intValue() ^ number2.intValue());
        }
        // number1 ^ other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() ^ value2.asInt());
        }
        return LzrNumber.of(number1.intValue() ^ value2.asInt());
    }
    
    private LzrValue lshift(LzrValue value1, LzrValue value2) {
        return switch (value1.type()) {
            case Types.NUMBER -> lshift((LzrNumber) value1, value2);
            case Types.ARRAY -> lshift((LzrArray) value1, value2);
            default -> throw new OperationIsNotSupportedException(operation,
                    "for " + Types.typeToString(value1.type()));
        };
    }

    private LzrValue lshift(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 << number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() << number2.longValue());
            }
            return LzrNumber.of(number1.intValue() << number2.intValue());
        }
        // number1 << other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() << value2.asInt());
        }
        return LzrNumber.of(number1.intValue() << value2.asInt());
    }

    private LzrValue lshift(LzrArray value1, LzrValue value2) {
        if (value2.type() != Types.ARRAY)
            throw new LZRException("TypeException", "Cannot merge non array value to array");
        return LzrArray.merge(value1, (LzrArray) value2);
    }
    
    private LzrValue rshift(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return rshift((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue rshift(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 >> number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() >> number2.longValue());
            }
            return LzrNumber.of(number1.intValue() >> number2.intValue());
        }
        // number1 >> other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() >> value2.asInt());
        }
        return LzrNumber.of(number1.intValue() >> value2.asInt());
    }
    
    private LzrValue urshift(LzrValue value1, LzrValue value2) {
        if (value1.type() == Types.NUMBER) {
            return urshift((LzrNumber) value1, value2);
        }
        throw new OperationIsNotSupportedException(operation,
                "for " + Types.typeToString(value1.type()));
    }
    
    private LzrValue urshift(LzrNumber value1, LzrValue value2) {
        final Number number1 = value1.raw();
        if (value2.type() == Types.NUMBER) {
            // number1 >>> number2
            final Number number2 = (Number) value2.raw();
            if (number1 instanceof Long || number2 instanceof Long) {
                return LzrNumber.of(number1.longValue() >>> number2.longValue());
            }
            return LzrNumber.of(number1.intValue() >>> number2.intValue());
        }
        // number1 >>> other
        if (number1 instanceof Long) {
            return LzrNumber.of(number1.longValue() >>> value2.asInt());
        }
        return LzrNumber.of(number1.intValue() >>> value2.asInt());
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
