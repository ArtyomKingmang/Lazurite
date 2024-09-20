package com.kingmang.lazurite.optimization;

import com.kingmang.lazurite.parser.ast.Accessible;
import com.kingmang.lazurite.parser.ast.Argument;
import com.kingmang.lazurite.parser.ast.Arguments;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.expressions.*;
import com.kingmang.lazurite.parser.ast.statements.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.kingmang.lazurite.patterns.visitor.VisitorUtils.isValue;
import static com.kingmang.lazurite.patterns.visitor.VisitorUtils.isVariable;

public class VariablesGrabber extends OptimizationVisitor<Map<String, VariableInfo>> {

    public static Map<String, VariableInfo> getInfo(Node node) {
        return getInfo(node, false);
    }

    public static Map<String, VariableInfo> getInfo(Node node, boolean grabModuleConstants) {
        Map<String, VariableInfo> variableInfos = new HashMap<>();
        node.accept(new VariablesGrabber(grabModuleConstants), variableInfos);
        return variableInfos;
    }

    private final boolean grabModuleConstants;

    public VariablesGrabber() {
        this(false);
    }

    public VariablesGrabber(boolean grabModuleConstants) {
        this.grabModuleConstants = grabModuleConstants;
    }

    @Override
    public Node visit(AssignmentExpression s, Map<String, VariableInfo> t) {
        if (!isVariable((Node)s.getTarget())) {
            return super.visit(s, t);
        }

        final String variableName = ((VariableExpression) s.getTarget()).getName();
        final VariableInfo var = variableInfo(t, variableName);

        if (s.getOperation() == null && isValue(s.getExpression())) {
            var.value = ((ValueExpression) s.getExpression()).getValue();
        }
        t.put(variableName, var);
        return super.visit(s, t);
    }

    @Override
    public Node visit(DestructuringAssignmentStatement s, Map<String, VariableInfo> t) {
        for (String variableName : s.getVariables()) {
            if (variableName == null) continue;
            t.put(variableName, variableInfo(t, variableName));
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(ForeachArrayStatement s, Map<String, VariableInfo> t) {
        t.put(s.getVariable(), variableInfo(t, s.getVariable()));
        return super.visit(s, t);
    }

    @Override
    public Node visit(ForeachMapStatement s, Map<String, VariableInfo> t) {
        t.put(s.getKey(), variableInfo(t, s.getKey()));
        t.put(s.getValue(), variableInfo(t, s.getValue()));
        return super.visit(s, t);
    }

    @Override
    public Node visit(MatchExpression s, Map<String, VariableInfo> t) {
        for (MatchExpression.Pattern pattern : s.getPatterns()) {
            if (pattern instanceof MatchExpression.VariablePattern) {
                final String variableName = ((MatchExpression.VariablePattern) pattern).getVariable();
                t.put(variableName, variableInfo(t, variableName));
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(UnaryExpression s, Map<String, VariableInfo> t) {
        if (s.getExpr1() instanceof Accessible) {
            if (s.getExpr1() instanceof VariableExpression) {
                final String variableName = ((VariableExpression) s.getExpr1()).getName();
                t.put(variableName, variableInfo(t, variableName));
            }
            if (s.getExpr1() instanceof ContainerAccessExpression) {
                ContainerAccessExpression conExpr = (ContainerAccessExpression) s.getExpr1();
                if (conExpr.rootIsVariable()) {
                    final String variableName = ((VariableExpression) conExpr.getRoot()).getName();
                    t.put(variableName, variableInfo(t, variableName));
                }
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(UsingStatement s, Map<String, VariableInfo> t) {
        // oh, fucking optimization
        return null;
    }

    private boolean canLoadConstants(Expression expression) {
        if (expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) expression;
            for (Expression expr : ae.getElements()) {
                if (!isValue(expr)) {
                    return false;
                }
            }
            return true;
        }
        return isValue(expression);
    }

    @Override
    protected boolean visit(Arguments in, Arguments out, Map<String, VariableInfo> t) {
        for (Argument argument : in) {
            final String variableName = argument.getName();
            final VariableInfo var = variableInfo(t, variableName);
            t.put(variableName, var);
        }
        return super.visit(in, out, t);
    }



    private VariableInfo variableInfo(Map<String, VariableInfo> t, final String variableName) {
        final VariableInfo var;
        if (t.containsKey(variableName)) {
            var = t.get(variableName);
            var.modifications++;
        } else {
            var = new VariableInfo();
            var.modifications = 1;
        }
        return var;
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
