package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.runtime.MapValue;
import com.kingmang.lazurite.runtime.Value;

import java.util.Iterator;
import java.util.Map;


public final class MapExpression implements Expression {
    
    public final Map<Expression, Expression> elements;

    public MapExpression(Map<Expression, Expression> arguments) {
        this.elements = arguments;
    }
    
    @Override
    public Value eval() {
        final int size = elements.size();
        final MapValue map = new MapValue(size);
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
