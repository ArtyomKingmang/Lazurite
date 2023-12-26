package com.kingmang.lazurite.parser.optimization;

import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.parser.ast.MStatement;
import com.kingmang.lazurite.parser.ast.Expression;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.PrintStatement;
import com.kingmang.lazurite.parser.ast.PrintlnStatement;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.ast.ValueExpression;
import com.kingmang.lazurite.parser.pars.VisitorUtils;


public class InstructionCombining extends OptimizationVisitor<Void> implements Optimizable {

    private int printCombinedCount;

    @Override
    public Node optimize(Node node) {
        return node.accept(this, null);
    }

    @Override
    public int optimizationsCount() {
        return printCombinedCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        if (printCombinedCount > 0) {
            sb.append("\nPrint statement combined: ").append(printCombinedCount);
        }
        return sb.toString();
    }

    @Override
    public Node visit(MStatement s, Void t) {
        final int size = s.statements.size();
        if (size <= 1) {
            return super.visit(s, t);
        }

        boolean changed = false;
        final MStatement result = new MStatement();
        int i;
        for (i = 1; i < size; i++) {
            Statement s1 = s.statements.get(i - 1);
            Statement s2 = s.statements.get(i);
            Node n1 = s1.accept(this, t);
            Node n2 = s2.accept(this, t);
            if (n1 != s1 || n2 != s2) {
                changed = true;
            }
            final Node combined = tryCombine(n1, n2);
            if (combined == null) {
                result.add((Statement) n1);
            } else {
                changed = true;
                result.add(consumeStatement(combined));
                i++;
            }
        }
        if (i == size) {
            // Last node
            Statement s2 = s.statements.get(size - 1);
            Node n2 = s2.accept(this, t);
            if (n2 != s2) {
                changed = true;
            }
            result.add((Statement) n2);
        }
        if (changed) {
            return result;
        }
        return super.visit(s, t);
    }

    private Node tryCombine(Node n1, Node n2) {
        final int n1Type;
        if (n1 instanceof PrintStatement) n1Type = 1;
        else if (n1 instanceof PrintlnStatement) n1Type = 2;
        else n1Type = 0;

        final int n2Type;
        if (n2 instanceof PrintStatement) n2Type = 1;
        else if (n2 instanceof PrintlnStatement) n2Type = 2;
        else n2Type = 0;

        if (n1Type != 0 && n2Type != 0) {
            final Expression e1 = (n1Type == 1)
                    ? ((PrintStatement) n1).expression
                    : ((PrintlnStatement) n1).expression;
            final Expression e2 = (n2Type == 1)
                    ? ((PrintStatement) n2).expression
                    : ((PrintlnStatement) n2).expression;
            if (VisitorUtils.isConstantValue(e1) && VisitorUtils.isConstantValue(e2)) {
                String s1 = e1.eval().asString();
                if (n1Type == 2) s1 += Console.newline();
                String s2 = e2.eval().asString();
                if (n2Type == 2) s2 += Console.newline();
                printCombinedCount++;
                return new PrintStatement(new ValueExpression(s1 + s2));
            }
        }
        return null;
    }
}
