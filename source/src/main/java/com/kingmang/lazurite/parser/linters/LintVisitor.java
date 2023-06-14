package com.kingmang.lazurite.parser.linters;

import com.kingmang.lazurite.parser.ast.IncludeStatement;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.ast.Visitor;
import com.kingmang.lazurite.parser.pars.AbstractVisitor;
import com.kingmang.lazurite.parser.pars.VisitorUtils;

public abstract class LintVisitor extends AbstractVisitor {

    protected void applyVisitor(IncludeStatement s, Visitor visitor) {
        final Statement program = VisitorUtils.includeProgram(s);
        if (program != null) {
            program.accept(visitor);
        }
    }
}
