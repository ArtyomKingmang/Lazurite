package com.kingmang.lazurite.optimization;


import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException;
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


public class ConstantFolding extends OptimizationVisitor<Void> implements Optimizable {

    private static final Set<String> OPERATORS = VisitorUtils.operators();

    private int binaryExpressionFoldingCount;
    private int conditionalExpressionFoldingCount;
    private int unaryExpressionFoldingCount;

    private final String DIVIDER = "\n=============================";
    private final Set<String> overloadedOperators;

    public ConstantFolding() {
        overloadedOperators = new HashSet<>();
    }

    @Override
    public Node optimize(Node node) {
        return node.accept(this, null);
    }

    @Override
    public int optimizationsCount() {
        return binaryExpressionFoldingCount
                + conditionalExpressionFoldingCount
                + unaryExpressionFoldingCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\t Constant Folding \t");
        sb.append(DIVIDER);
        if (binaryExpressionFoldingCount > 0) {
            sb.append("\nBinaryExpression: ").append(binaryExpressionFoldingCount);
        }
        if (conditionalExpressionFoldingCount > 0) {
            sb.append("\nConditionalExpression: ").append(conditionalExpressionFoldingCount);
        }
        if (unaryExpressionFoldingCount > 0) {
            sb.append("\nUnaryExpression: ").append(unaryExpressionFoldingCount);
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
        if ( (s.getExpr1() instanceof ValueExpression) && (s.getExpr2() instanceof ValueExpression) ) {
            binaryExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                binaryExpressionFoldingCount--;
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(ConditionalExpression s, Void t) {
        if (overloadedOperators.contains(s.getOperation().getText())) {
            return super.visit(s, t);
        }
        if ( (s.getExpr1() instanceof ValueExpression) && (s.getExpr2() instanceof ValueExpression) ) {
            conditionalExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                conditionalExpressionFoldingCount--;
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(UnaryExpression s, Void t) {
        if (overloadedOperators.contains(s.getOperation().toString())) {
            return super.visit(s, t);
        }
        if (s.getExpr1() instanceof ValueExpression) {
            unaryExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                unaryExpressionFoldingCount--;
            }
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
