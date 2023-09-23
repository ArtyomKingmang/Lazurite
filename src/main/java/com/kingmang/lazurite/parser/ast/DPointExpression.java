package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.base.KEYWORD;


public final class DPointExpression extends InterruptableNode implements Expression {

    public final String name;

    public DPointExpression(String name) {
        this.name = name;
    }

    @Override
    public LZRFunction eval() {
        super.interruptionCheck();
        return new LZRFunction(KEYWORD.get(name));
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
