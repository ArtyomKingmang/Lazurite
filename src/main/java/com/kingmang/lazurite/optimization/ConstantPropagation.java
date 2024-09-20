package com.kingmang.lazurite.optimization;

import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.expressions.ValueExpression;
import com.kingmang.lazurite.parser.ast.expressions.VariableExpression;
import com.kingmang.lazurite.parser.ast.statements.AssertStatement;
import com.kingmang.lazurite.parser.ast.statements.DoWhileStatement;
import com.kingmang.lazurite.parser.ast.statements.TryCatchStatement;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConstantPropagation extends OptimizationVisitor<Map<String, LzrValue>> implements Optimizable {

    private final Map<String, Integer> propagatedVariables;

    public ConstantPropagation() {
        propagatedVariables = new HashMap<>();
    }

    private final String DIVIDER = "\n=============================";

    @Override
    public Node optimize(Node node) {
        final Map<String, VariableInfo> variables = new HashMap<>();
        node.accept(new VariablesGrabber(true), variables);
        final Map<String, LzrValue> candidates = new HashMap<>();
        for (Map.Entry<String, VariableInfo> e : variables.entrySet()) {
            final VariableInfo info = e.getValue();
            if (info.modifications != 1) continue;
            if (info.value == null) continue;
            switch (info.value.type()) {
                case Types.NUMBER:
                case Types.STRING:
                    candidates.put(e.getKey(), info.value);
                    break;
            }
        }
        return node.accept(this, candidates);
    }

    @Override
    public int optimizationsCount() {
        return propagatedVariables.size();
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\t Constant Propagation \t");
        sb.append(DIVIDER);
        if (propagatedVariables.size() > 0) {
            sb.append("\nConstant propagations: ").append(propagatedVariables.size());
            for (Map.Entry<String, Integer> e : propagatedVariables.entrySet()) {
                sb.append("\n  ").append(e.getKey()).append(": ").append(e.getValue());
            }
        }
        sb.append(DIVIDER);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Node visit(VariableExpression s, Map<String, LzrValue> t) {
        if (t.containsKey(s.getName())) {
            if (!propagatedVariables.containsKey(s.getName())) {
                propagatedVariables.put(s.getName(), 1);
            } else {
                propagatedVariables.put(s.getName(), 1 + propagatedVariables.get(s.getName()));
            }
            return new ValueExpression(t.get(s.getName()));
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(@NotNull AssertStatement s, Map<String, LzrValue> stringLzrValueMap) {
        return null;
    }

    @Override
    public Node visit(@NotNull TryCatchStatement s, Map<String, LzrValue> stringLzrValueMap) {
        return null;
    }

    @Override
    public Node visit(@NotNull DoWhileStatement s, Map<String, LzrValue> stringLzrValueMap) {
        return null;
    }
}
