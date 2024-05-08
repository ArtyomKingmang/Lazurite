package com.kingmang.lazurite.patterns.visitor;

import com.kingmang.lazurite.parser.AST.Expressions.ArrayExpression;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.parser.AST.Statements.UsingStatement;
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression;

import java.util.HashSet;
import java.util.Set;

public class LibsDetector extends AbstractVisitor {

    private Set<String> modules;

    public LibsDetector() {
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
