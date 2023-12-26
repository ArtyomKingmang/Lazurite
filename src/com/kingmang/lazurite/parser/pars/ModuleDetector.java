package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.parser.ast.ArrayExpression;
import com.kingmang.lazurite.parser.ast.Expression;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.ast.UsingStatement;
import com.kingmang.lazurite.parser.ast.ValueExpression;

import java.util.HashSet;
import java.util.Set;

public class ModuleDetector extends AbstractVisitor {

    private Set<String> modules;

    public ModuleDetector() {
        modules = new HashSet<>();
    }

    public Set<String> detect(Statement s) {
        s.accept(this);
        return modules;
    }

    @Override
    public void visit(UsingStatement st) {
        if (st.expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) st.expression;
            for (Expression expr : ae.elements) {
                modules.add(expr.eval().asString());
            }
        }
        if (st.expression instanceof ValueExpression) {
            ValueExpression ve = (ValueExpression) st.expression;
            modules.add(ve.value.asString());
        }
        super.visit(st);
    }
}
