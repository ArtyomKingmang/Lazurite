package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.libraries.Keyword;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class DPointExpression extends InterruptableNode implements Expression {

    public final String name;

    @Override
    public LzrFunction eval() {
        super.interruptionCheck();
        return new LzrFunction(Keyword.get(name));
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
