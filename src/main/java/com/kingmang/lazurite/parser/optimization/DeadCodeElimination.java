package com.kingmang.lazurite.parser.optimization;

import com.kingmang.lazurite.lib.Types;
import com.kingmang.lazurite.parser.ast.AssignmentExpression;
import com.kingmang.lazurite.parser.ast.MStatement;
import com.kingmang.lazurite.parser.ast.ExprStatement;
import com.kingmang.lazurite.parser.ast.Expression;
import com.kingmang.lazurite.parser.ast.IfStatement;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.ast.TernaryExpression;
import com.kingmang.lazurite.parser.ast.ValueExpression;
import com.kingmang.lazurite.parser.ast.VariableExpression;
import com.kingmang.lazurite.parser.ast.WhileStatement;
import com.kingmang.lazurite.parser.pars.VisitorUtils;

import java.util.Map;


public class DeadCodeElimination extends OptimizationVisitor<Map<String, VariableInfo>> implements Optimizable {

    private int ifStatementEliminatedCount;
    private int ternaryExpressionEliminatedCount;
    private int whileStatementEliminatedCount;
    private int assignmentExpressionEliminatedCount;

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
        return sb.toString();
    }

    @Override
    public Node visit(IfStatement s, Map<String, VariableInfo> t) {
        if (VisitorUtils.isValue(s.expression)) {
            ifStatementEliminatedCount++;
            // true statement
            if (s.expression.eval().asInt() != 0) {
                return s.ifStatement;
            }
            // false statement
            if (s.elseStatement != null) {
                return s.elseStatement;
            }
            return new ExprStatement(s.expression);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(TernaryExpression s, Map<String, VariableInfo> t) {
        if (VisitorUtils.isValue(s.condition)) {
            ternaryExpressionEliminatedCount++;
            if (s.condition.eval().asInt() != 0) {
                return s.trueExpr;
            }
            return s.falseExpr;
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(WhileStatement s, Map<String, VariableInfo> t) {
        if (VisitorUtils.isValueAsInt(s.condition, 0)) {
            whileStatementEliminatedCount++;
            return new ExprStatement(s.condition);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(AssignmentExpression s, Map<String, VariableInfo> t) {
        if (!VisitorUtils.isVariable((Node)s.target)) return super.visit(s, t);

        final String variableName = ((VariableExpression) s.target).name;
        if (!t.containsKey(variableName)) return super.visit(s, t);

        final VariableInfo info = t.get(((VariableExpression) s.target).name);
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
    public Node visit(MStatement s, Map<String, VariableInfo> t) {
        final MStatement result = new MStatement();
        boolean changed = false;
        for (Statement statement : s.statements) {
            final Node node = statement.accept(this, t);
            if (node != statement) {
                changed = true;
            }
            if (node instanceof ExprStatement
                    && VisitorUtils.isConstantValue( ((ExprStatement) node).expr )) {
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
}
