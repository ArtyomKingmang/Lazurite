package com.kingmang.lazurite.optimization;

import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.expressions.BinaryExpression;
import com.kingmang.lazurite.parser.ast.expressions.ConditionalExpression;
import com.kingmang.lazurite.parser.ast.expressions.UnaryExpression;
import com.kingmang.lazurite.parser.ast.expressions.ValueExpression;
import com.kingmang.lazurite.parser.ast.statements.AssertStatement;
import com.kingmang.lazurite.parser.ast.statements.DoWhileStatement;
import com.kingmang.lazurite.parser.ast.statements.FunctionDefineStatement;
import com.kingmang.lazurite.parser.ast.statements.TryCatchStatement;
import com.kingmang.lazurite.patterns.visitor.VisitorUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.kingmang.lazurite.patterns.visitor.VisitorUtils.isIntegerValue;
import static com.kingmang.lazurite.patterns.visitor.VisitorUtils.isSameVariables;

public class ExpressionSimplification extends OptimizationVisitor<Void> implements Optimizable {

    private static final Set<String> OPERATORS = VisitorUtils.operators();

    private int simplificationsCount;

    private final Set<String> overloadedOperators;

    private final String DIVIDER = "\n=============================";

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
        sb.append("\n\t Expression Simplification \t");
        sb.append(DIVIDER);
        if (simplificationsCount > 0) {
            sb.append("\nExpression simplifications: ").append(simplificationsCount);
        }
        sb.append(DIVIDER);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Node visit(BinaryExpression s, Void t) {
        if (overloadedOperators.contains(s.getOperation().toString())) {
            return super.visit(s, t);
        }
        // operations with 0
        final boolean expr1IsZero = isIntegerValue(s.getExpr1(), 0);
        if (expr1IsZero || isIntegerValue(s.getExpr2(), 0)) {
            switch (s.getOperation()) {
                case ADD:
                    //  0 + x = x + 0 = x
                    simplificationsCount++;
                    return expr1IsZero ? s.getExpr2() : s.getExpr1();

                case SUBTRACT:
                    simplificationsCount++;
                    if (expr1IsZero) {
                        // 0 - x = -x
                        return new UnaryExpression(UnaryExpression.Operator.NEGATE, s.getExpr2());
                    }
                    // x - 0 = x
                    return s.getExpr1();

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
        final boolean expr1IsOne = isIntegerValue(s.getExpr1(), 1);
        if (expr1IsOne || isIntegerValue(s.getExpr2(), 1)) {
            switch (s.getOperation()) {
                case MULTIPLY:
                    // 1 * x = x * 1 = x
                    simplificationsCount++;
                    return expr1IsOne ? s.getExpr2() : s.getExpr1();

                case DIVIDE:
                    // x / 1 = x
                    if (!expr1IsOne) {
                        simplificationsCount++;
                        return s.getExpr1();
                    }
                    break;
            }
        }

        // x / -1 = -x
        if (isIntegerValue(s.getExpr2(), -1) && s.getOperation() == BinaryExpression.Operator.DIVIDE) {
            simplificationsCount++;
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, s.getExpr1());
        }

        // -1 * x = x * -1 = -x
        final boolean expr1IsMinusOne = isIntegerValue(s.getExpr1(), -1);
        if ((expr1IsMinusOne || isIntegerValue(s.getExpr2(), -1)) && s.getOperation() == BinaryExpression.Operator.MULTIPLY) {
            simplificationsCount++;
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, expr1IsMinusOne ? s.getExpr2() : s.getExpr1());
        }

        // x - x = 0
        if (isSameVariables(s.getExpr1(), s.getExpr2()) && s.getOperation() == BinaryExpression.Operator.SUBTRACT) {
            simplificationsCount++;
            return new ValueExpression(0);
        }

        // x >> 0 = x, x << 0 = x
        if (isIntegerValue(s.getExpr2(), 0) &&
                (s.getOperation() == BinaryExpression.Operator.LSHIFT ||
                 s.getOperation() == BinaryExpression.Operator.RSHIFT)) {
            simplificationsCount++;
            return s.getExpr1();
        }

        return super.visit(s, t);
    }

    @Override
    public Node visit(ConditionalExpression s, Void t) {
        if (overloadedOperators.contains(s.getOperation().getText())) {
            return super.visit(s, t);
        }
        if (isIntegerValue(s.getExpr1(), 0) && s.getOperation() == ConditionalExpression.Operator.AND) {
            // 0 && x = 0
            simplificationsCount++;
            return new ValueExpression(0);
        }
        if (isIntegerValue(s.getExpr1(), 1) && s.getOperation() == ConditionalExpression.Operator.OR) {
            // 1 || x = 1
            simplificationsCount++;
            return new ValueExpression(1);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(FunctionDefineStatement s, Void t) {
        if (OPERATORS.contains(s.getName())) {
            overloadedOperators.add(s.getName());
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(@NotNull AssertStatement s, Void unused) {
        return null;
    }

    @Override
    public Node visit(@NotNull TryCatchStatement s, Void unused) {
        return null;
    }

    @Override
    public Node visit(@NotNull DoWhileStatement s, Void unused) {
        return null;
    }
}
