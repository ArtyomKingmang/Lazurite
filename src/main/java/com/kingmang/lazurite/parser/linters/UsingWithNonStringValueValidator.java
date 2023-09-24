package com.kingmang.lazurite.parser.linters;

import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.parser.ast.*;

public final class UsingWithNonStringValueValidator extends LintVisitor {

    @Override
    public void visit(IncludeStatement st) {
        super.visit(st);
        applyVisitor(st, this);
    }

    @Override
    public void visit(UsingStatement st) {
        super.visit(st);

        if (st.expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) st.expression;
            for (Expression expr : ae.elements) {
                if (!checkExpression(expr)) {
                    return;
                }
            }
        } else {
            if (!checkExpression(st.expression)) {
                return;
            }
        }
    }

    private boolean checkExpression(Expression expr) {
        if (!(expr instanceof ValueExpression)) {
            Console.error(String.format(
                    "Warning: `using` with %s, not ValueExpression", expr.getClass().getSimpleName()));
            return false;
        }

        final Value value = ((ValueExpression) expr).value;
        if (value.type() != Types.STRING) {
            warnWrongType(value);
            return false;
        }
        return true;
    }

    private void warnWrongType(Value value) {
        Console.error(String.format(
                "Warning: `using` with %s - %s, not string",
                Types.typeToString(value.type()), value.asString()));
    }
}
