package com.kingmang.lazurite.patterns.visitor;

import com.kingmang.lazurite.parser.AST.Statements.FunctionDefineStatement;


public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }
}
