package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.parser.AST.Statements.ReturnStatement;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Variables;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public final class MatchExpression extends InterruptableNode implements Expression, Statement {

    public final Expression expression;
    public final List<Pattern> patterns;

    @Override
    public void execute() {
        eval();
    }

    @Override
    public LzrValue eval() {
        super.interruptionCheck();
        final LzrValue value = expression.eval();
        for (Pattern p : patterns) {
            if (p instanceof ConstantPattern pattern) {
                if (match(value, pattern.constant) && optMatches(p)) {
                    return evalResult(p.result);
                }
            }
            if (p instanceof VariablePattern pattern) {
                if (pattern.variable.equals("_")) return evalResult(p.result);

                if (Variables.isExists(pattern.variable)) {
                    if (match(value, Variables.get(pattern.variable)) && optMatches(p)) {
                        return evalResult(p.result);
                    }
                } else {
                    Variables.define(pattern.variable, value);
                    if (optMatches(p)) {
                        final LzrValue result = evalResult(p.result);
                        Variables.remove(pattern.variable);
                        return result;
                    }
                    Variables.remove(pattern.variable);
                }
            }
            if ((value.type() == Types.ARRAY) && (p instanceof ListPattern pattern)) {
                if (matchListPattern((LzrArray) value, pattern)) {

                    final LzrValue result = evalResult(p.result);
                    for (String var : pattern.parts) {
                        Variables.remove(var);
                    }
                    return result;
                }
            }
            if ((value.type() == Types.ARRAY) && (p instanceof TuplePattern pattern)) {
                if (matchTuplePattern((LzrArray) value, pattern) && optMatches(p)) {
                    return evalResult(p.result);
                }
            }
        }
        throw new LZRException("PatternMatchingException ","No pattern were matched");
    }

    private boolean matchTuplePattern(LzrArray array, TuplePattern p) {
        if (p.values.size() != array.size()) return false;

        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final Expression expr = p.values.get(i);
            if ( (expr != TuplePattern.ANY) && (expr.eval().compareTo(array.get(i)) != 0) ) {
                return false;
            }
        }
        return true;
    }

    private boolean matchListPattern(LzrArray array, ListPattern p) {
        final List<String> parts = p.parts;
        final int partsSize = parts.size();
        final int arraySize = array.size();
        switch (partsSize) {
            case 0:
                return (arraySize == 0) && optMatches(p);

            case 1:
                final String variable = parts.get(0);
                Variables.define(variable, array);
                if (optMatches(p)) {
                    return true;
                }
                Variables.remove(variable);
                return false;

            default: {
                if (partsSize == arraySize) {

                    return matchListPatternEqualsSize(p, parts, partsSize, array);
                } else if (partsSize < arraySize) {

                    return matchListPatternWithTail(p, parts, partsSize, array, arraySize);
                }
                return false;
            }
        }
    }

    private boolean matchListPatternEqualsSize(ListPattern p, List<String> parts, int partsSize, LzrArray array) {

        for (int i = 0; i < partsSize; i++) {
            Variables.define(parts.get(i), array.get(i));
        }
        if (optMatches(p)) {

            return true;
        }

        for (String var : parts) {
            Variables.remove(var);
        }
        return false;
    }

    private boolean matchListPatternWithTail(ListPattern p, List<String> parts, int partsSize, LzrArray array, int arraySize) {

        final int lastPart = partsSize - 1;
        for (int i = 0; i < lastPart; i++) {
            Variables.define(parts.get(i), array.get(i));
        }

        final LzrArray tail = new LzrArray(arraySize - partsSize + 1);
        for (int i = lastPart; i < arraySize; i++) {
            tail.set(i - lastPart, array.get(i));
        }
        Variables.define(parts.get(lastPart), tail);

        if (optMatches(p)) {

            return true;
        }

        for (String var : parts) {
            Variables.remove(var);
        }
        return false;
    }

    private boolean match(LzrValue value, LzrValue constant) {
        if (value.type() != constant.type()) return false;
        return value.equals(constant);
    }

    private boolean optMatches(Pattern pattern) {
        if (pattern.optCondition == null) return true;
        return pattern.optCondition.eval() != LzrNumber.ZERO;
    }

    private LzrValue evalResult(Statement s) {
        try {
            s.execute();
        } catch (ReturnStatement ret) {
            return ret.getResult();
        }
        return LzrNumber.ZERO;
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
        final StringBuilder sb = new StringBuilder();
        sb.append("match ").append(expression).append(" {");
        for (Pattern p : patterns) {
            sb.append("\n  case ").append(p);
        }
        sb.append("\n}");
        return sb.toString();
    }

    public abstract static class Pattern {
        public Statement result;
        public Expression optCondition;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (optCondition != null) {
                sb.append(" if ").append(optCondition);
            }
            sb.append(": ").append(result);
            return sb.toString();
        }
    }

    public static class ConstantPattern extends Pattern {
        LzrValue constant;

        public ConstantPattern(LzrValue pattern) {
            this.constant = pattern;
        }

        @Override
        public String toString() {
            return constant.toString().concat(super.toString());
        }
    }

    public static class VariablePattern extends Pattern {
        public String variable;

        public VariablePattern(String pattern) {
            this.variable = pattern;
        }

        @Override
        public String toString() {
            return variable.concat(super.toString());
        }
    }

    public static class ListPattern extends Pattern {
        List<String> parts;

        public ListPattern() {
            this(new ArrayList<>());
        }

        ListPattern(List<String> parts) {
            this.parts = parts;
        }

        public void add(String part) {
            parts.add(part);
        }

        @Override
        public String toString() {
            final Iterator<String> it = parts.iterator();
            if (it.hasNext()) {
                final StringBuilder sb = new StringBuilder();
                sb.append("[").append(it.next());
                while (it.hasNext()) {
                    sb.append(" :: ").append(it.next());
                }
                sb.append("]").append(super.toString());
                return sb.toString();
            }
            return "[]".concat(super.toString());
        }
    }

    public static class TuplePattern extends Pattern {
        public List<Expression> values;

        public TuplePattern() {
            this(new ArrayList<>());
        }

        public TuplePattern(List<Expression> parts) {
            this.values = parts;
        }

        public void addAny() {
            values.add(ANY);
        }

        public void add(Expression value) {
            values.add(value);
        }

        @Override
        public String toString() {
            final Iterator<Expression> it = values.iterator();
            if (it.hasNext()) {
                final StringBuilder sb = new StringBuilder();
                sb.append('(').append(it.next());
                while (it.hasNext()) {
                    sb.append(", ").append(it.next());
                }
                sb.append(')').append(super.toString());
                return sb.toString();
            }
            return "()".concat(super.toString());
        }

        private static final Expression ANY = new Expression() {
            @Override
            public LzrValue eval() {
                return LzrNumber.ONE;
            }

            @Override
            public void accept(Visitor visitor) {
            }

            @Override
            public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
                return null;
            }

            @Override
            public String toString() {
                return "_".concat(super.toString());
            }
        };
    }
}
