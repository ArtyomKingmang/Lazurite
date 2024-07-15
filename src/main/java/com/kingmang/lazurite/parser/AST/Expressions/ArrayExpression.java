package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.List;

public record ArrayExpression(List<Expression> elements) implements Expression {

    @Override
    public LzrValue eval() {
        final int size = elements.size();
        return new LzrArray(size, index -> elements.get(index).eval());
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
        return elements.toString();
    }
}
