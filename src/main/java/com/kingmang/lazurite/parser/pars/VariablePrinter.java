package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.parser.pars.AbstractVisitor;
import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.parser.ast.AssignmentExpression;
import com.kingmang.lazurite.parser.ast.VariableExpression;


public final class VariablePrinter extends AbstractVisitor {

    @Override
    public void visit(AssignmentExpression s) {
        super.visit(s);
        Console.println(s.target);
    }

    @Override
    public void visit(VariableExpression s) {
        super.visit(s);
        Console.println(s.name);
    }
}
