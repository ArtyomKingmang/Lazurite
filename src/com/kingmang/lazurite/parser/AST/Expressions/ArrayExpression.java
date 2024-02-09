package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.runtime.Value;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class ArrayExpression implements Expression {
    
    public final List<Expression> elements;

    @Override
    public Value eval() {
        final int size = elements.size();
        final LzrArray array = new LzrArray(size);
        for (int i = 0; i < size; i++) {
            array.set(i, elements.get(i).eval());
        }
        return array;
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
