package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.parser.ast.FunctionDefineStatement;
import com.kingmang.lazurite.parser.pars.AbstractVisitor;


public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }
}
