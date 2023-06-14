package com.kingmang.lazurite.parser.optimization;

import com.kingmang.lazurite.parser.ast.BinaryExpression;
import com.kingmang.lazurite.parser.ast.ConditionalExpression;
import com.kingmang.lazurite.parser.ast.FunctionDefineStatement;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.UnaryExpression;
import com.kingmang.lazurite.parser.ast.ValueExpression;
import com.kingmang.lazurite.parser.pars.VisitorUtils;

import java.util.HashSet;
import java.util.Set;


public class ExpressionSimplification extends OptimizationVisitor<Void> implements Optimizable {

    private static final Set<String> OPERATORS = VisitorUtils.operators();

    private int simplificationsCount;

    private final Set<String> overloadedOperators;

    public ExpressionSimplification() {
        simplificationsCount = 0;
        overloadedOperators = new HashSet<>();
    }

    @Override
    public Node optimize(Node node) {
        return node.accept(this, null);
    }

    @Override
    public int optimizationsCount() {
        return simplificationsCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        if (simplificationsCount > 0) {
            sb.append("\nExpression simplifications: ").append(simplificationsCount);
        }
        return sb.toString();
    }

    @Override
    public Node visit(BinaryExpression s, Void t) {
        if (overloadedOperators.contains(s.operation.toString())) {
            return super.visit(s, t);
        }
        // operations with 0
        final boolean expr1IsZero = VisitorUtils.isIntegerValue(s.expr1, 0);
        if (expr1IsZero || VisitorUtils.isIntegerValue(s.expr2, 0)) {
            switch (s.operation) {
                case ADD:
                    //  0 + x = x + 0 = x
                    simplificationsCount++;
                    return expr1IsZero ? s.expr2 : s.expr1;

                case SUBTRACT:
                    simplificationsCount++;
                    if (expr1IsZero) {
                        // 0 - x = -x
                        return new UnaryExpression(UnaryExpression.Operator.NEGATE, s.expr2);
                    }
                    // x - 0 = x
                    return s.expr1;

                case MULTIPLY:
                    // 0 * x = x * 0 = 0
                    simplificationsCount++;
                    return new ValueExpression(0);

                case DIVIDE:
                    // 0 / x = 0
                    if (expr1IsZero) {
                        simplificationsCount++;
                        return new ValueExpression(0);
                    }
                    break;
            }
        }

        // operations with 1
        final boolean expr1IsOne = VisitorUtils.isIntegerValue(s.expr1, 1);
        if (expr1IsOne || VisitorUtils.isIntegerValue(s.expr2, 1)) {
            switch (s.operation) {
                case MULTIPLY:
                    // 1 * x = x * 1 = x
                    simplificationsCount++;
                    return expr1IsOne ? s.expr2 : s.expr1;

                case DIVIDE:
                    // x / 1 = x
                    if (!expr1IsOne) {
                        simplificationsCount++;
                        return s.expr1;
                    }
                    break;
            }
        }

        // x / -1 = -x
        if (VisitorUtils.isIntegerValue(s.expr2, -1) && s.operation == BinaryExpression.Operator.DIVIDE) {
            simplificationsCount++;
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, s.expr1);
        }

        // -1 * x = x * -1 = -x
        final boolean expr1IsMinusOne = VisitorUtils.isIntegerValue(s.expr1, -1);
        if ((expr1IsMinusOne || VisitorUtils.isIntegerValue(s.expr2, -1)) && s.operation == BinaryExpression.Operator.MULTIPLY) {
            simplificationsCount++;
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, expr1IsMinusOne ? s.expr2 : s.expr1);
        }

        // x - x = 0
        if (VisitorUtils.isSameVariables(s.expr1, s.expr2) && s.operation == BinaryExpression.Operator.SUBTRACT) {
            simplificationsCount++;
            return new ValueExpression(0);
        }

        // x >> 0 = x, x << 0 = x
        if (VisitorUtils.isIntegerValue(s.expr2, 0) &&
                (s.operation == BinaryExpression.Operator.LSHIFT ||
                 s.operation == BinaryExpression.Operator.RSHIFT)) {
            simplificationsCount++;
            return s.expr1;
        }

        return super.visit(s, t);
    }

    @Override
    public Node visit(ConditionalExpression s, Void t) {
        if (overloadedOperators.contains(s.operation.getName())) {
            return super.visit(s, t);
        }
        if (VisitorUtils.isIntegerValue(s.expr1, 0) && s.operation == ConditionalExpression.Operator.AND) {
            // 0 && x = 0
            simplificationsCount++;
            return new ValueExpression(0);
        }
        if (VisitorUtils.isIntegerValue(s.expr1, 1) && s.operation == ConditionalExpression.Operator.OR) {
            // 1 || x = 1
            simplificationsCount++;
            return new ValueExpression(1);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(FunctionDefineStatement s, Void t) {
        if (OPERATORS.contains(s.name)) {
            overloadedOperators.add(s.name);
        }
        return super.visit(s, t);
    }
}
