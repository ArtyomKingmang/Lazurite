package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.libraries.Keyword;


public final class DPointExpression extends InterruptableNode implements Expression {

    public final String name;

    public DPointExpression(String name) {
        this.name = name;
    }

    @Override
    public LZRFunction eval() {
        super.interruptionCheck();
        return new LZRFunction(Keyword.get(name));
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "::" + name;
    }
}
