package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.Map;

public record MapExpression(Map<Expression, Expression> elements) implements Expression {

    @Override
    public LzrValue eval() {
        final int size = elements.size();
        final LzrMap map = new LzrMap(size);
        for (Map.Entry<Expression, Expression> entry : elements.entrySet()) {
            map.set(entry.getKey().eval(), entry.getValue().eval());
        }
        return map;
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
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        Iterator<Map.Entry<Expression, Expression>> it = elements.entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<Expression, Expression> entry = it.next();
            sb.append(entry.getKey()).append(" : ").append(entry.getValue());
            while (it.hasNext()) {
                entry = it.next();
                sb.append(", ");
                sb.append(entry.getKey()).append(" : ").append(entry.getValue());
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
