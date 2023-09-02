package com.kingmang.lazurite.parser.linters;

import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.lib.KEYWORD;
import com.kingmang.lazurite.parser.ast.FunctionDefineStatement;
import com.kingmang.lazurite.parser.ast.IncludeStatement;
import com.kingmang.lazurite.parser.ast.UsingStatement;

public final class DefaultFunctionsOverrideValidator extends LintVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        if (KEYWORD.isExists(s.name)) {
            Console.error(String.format(
                    "Warning: function \"%s\" overrides default module function", s.name));
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
