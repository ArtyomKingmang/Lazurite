package com.kingmang.lazurite.parser.linters;

import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.parser.ast.AssignmentExpression;
import com.kingmang.lazurite.parser.ast.IncludeStatement;
import com.kingmang.lazurite.parser.ast.UsingStatement;
import com.kingmang.lazurite.parser.ast.VariableExpression;


public final class AssignValidator extends LintVisitor {

    @Override
    public void visit(AssignmentExpression s) {
        super.visit(s);
        if (s.target instanceof VariableExpression) {
            final String variable = ((VariableExpression) s.target).name;
            if (Variables.isExists(variable)) {
                Console.error(String.format(
                    "Warning: variable \"%s\" overrides constant", variable));
            }
        }
    }

    @Override
    public void visit(IncludeStatement st) {
        super.visit(st);
        applyVisitor(st, this);
    }

    @Override
    public void visit(UsingStatement st) {
        super.visit(st);
        st.execute();
    }
}
