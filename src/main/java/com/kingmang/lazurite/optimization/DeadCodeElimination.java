package com.kingmang.lazurite.optimization;


import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.expressions.*;
import com.kingmang.lazurite.parser.ast.statements.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.kingmang.lazurite.patterns.visitor.VisitorUtils.*;

public class DeadCodeElimination extends OptimizationVisitor<Map<String, VariableInfo>> implements Optimizable {

    private int ifStatementEliminatedCount;
    private int ternaryExpressionEliminatedCount;
    private int whileStatementEliminatedCount;
    private int assignmentExpressionEliminatedCount;
    private final String DIVIDER = "\n=============================";

    @Override
    public Node optimize(Node node) {
        final Map<String, VariableInfo> variableInfos = VariablesGrabber.getInfo(node);
        return node.accept(this, variableInfos);
    }

    @Override
    public int optimizationsCount() {
        return ifStatementEliminatedCount + ternaryExpressionEliminatedCount
                + whileStatementEliminatedCount + assignmentExpressionEliminatedCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\t Dead Code Elimination \t");
        sb.append(DIVIDER);
        if (ifStatementEliminatedCount > 0) {
            sb.append("\nEliminated IfStatement: ").append(ifStatementEliminatedCount);
        }
        if (ternaryExpressionEliminatedCount > 0) {
            sb.append("\nEliminated TernaryExpression: ").append(ternaryExpressionEliminatedCount);
        }
        if (whileStatementEliminatedCount > 0) {
            sb.append("\nEliminated WhileStatement: ").append(whileStatementEliminatedCount);
        }
        if (whileStatementEliminatedCount > 0) {
            sb.append("\nEliminated AssignmentExpression: ").append(assignmentExpressionEliminatedCount);
        }
        sb.append(DIVIDER);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Node visit(IfStatement s, Map<String, VariableInfo> t) {
        if (isValue(s.getExpression())) {
            ifStatementEliminatedCount++;
            // true statement
            if (s.getExpression().eval().asInt() != 0) {
                return s.getIfStatement();
            }
            // false statement
            if (s.getElseStatement() != null) {
                return s.getElseStatement();
            }
            return new ExprStatement(s.getExpression());
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(TernaryExpression s, Map<String, VariableInfo> t) {
        if (isValue(s.getCondition())) {
            ternaryExpressionEliminatedCount++;
            if (s.getCondition().eval().asInt() != 0) {
                return s.getTrueExpr();
            }
            return s.getFalseExpr();
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(WhileStatement s, Map<String, VariableInfo> t) {
        if (isValueAsInt(s.getCondition(), 0)) {
            whileStatementEliminatedCount++;
            return new ExprStatement(s.getCondition());
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(AssignmentExpression s, Map<String, VariableInfo> t) {
        if (!isVariable((Node)s.getTarget())) return super.visit(s, t);

        final String variableName = ((VariableExpression) s.getTarget()).getName();
        if (!t.containsKey(variableName)) return super.visit(s, t);

        final VariableInfo info = t.get(((VariableExpression) s.getTarget()).getName());
        if (info.modifications != 1 || info.value == null) {
            return super.visit(s, t);
        }
        
        switch (info.value.type()) {
            case Types.NUMBER:
            case Types.STRING:
                assignmentExpressionEliminatedCount++;
                return new ValueExpression(info.value);
            default:
                return super.visit(s, t);
        }
    }

    @Override
    public Node visit(BlockStatement s, Map<String, VariableInfo> t) {
        final BlockStatement result = new BlockStatement();
        boolean changed = false;
        for (Statement statement : s.getStatements()) {
            final Node node = statement.accept(this, t);
            if (node != statement) {
                changed = true;
            }
            if (node instanceof ExprStatement
                    && isConstantValue( ((ExprStatement) node).getExpr() )) {
                changed = true;
                continue;
            }

            if (node instanceof Statement) {
                result.add((Statement) node);
            } else if (node instanceof Expression) {
                result.add(new ExprStatement((Expression) node));
            }
        }
        if (changed) {
            return result;
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(@NotNull AssertStatement s, Map<String, VariableInfo> stringVariableInfoMap) {
        return null;
    }

    @Override
    public Node visit(@NotNull TryCatchStatement s, Map<String, VariableInfo> stringVariableInfoMap) {
        return null;
    }

    @Override
    public Node visit(@NotNull DoWhileStatement s, Map<String, VariableInfo> stringVariableInfoMap) {
        return null;
    }
}
