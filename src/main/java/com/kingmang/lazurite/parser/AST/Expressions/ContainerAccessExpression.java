package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.parser.AST.Accessible;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.Getter;


import java.util.List;


@Getter
public final class ContainerAccessExpression implements Expression, Accessible {

    public final Expression root;
    public final List<Expression> indices;
    private final boolean rootIsVariable;

    public ContainerAccessExpression(String variable, List<Expression> indices) {
        this(new VariableExpression(variable), indices);
    }

    public ContainerAccessExpression(Expression root, List<Expression> indices) {
        rootIsVariable = root instanceof VariableExpression;
        this.root = root;
        this.indices = indices;
    }


    @Override
    public LzrValue eval() {
        return get();
    }
    
    @Override
    public LzrValue get() {
        final LzrValue container = getContainer();
        final LzrValue lastIndex = lastIndex();
        return switch (container.type()) {
            case Types.ARRAY -> ((LzrArray) container).get(lastIndex);
            case Types.MAP -> ((LzrMap) container).get(lastIndex);
            case Types.STRING -> ((LzrString) container).access(lastIndex);
            case Types.CLASS -> ((ClassInstanceValue) container).access(lastIndex);
            default ->
                    throw new LzrException("TypeException", "Array or map expected. Got " + Types.typeToString(container.type()));
        };
    }

    @Override
    public LzrValue set(LzrValue value) {
        final LzrValue container = getContainer();
        final LzrValue lastIndex = lastIndex();
        switch (container.type()) {
            case Types.ARRAY:
                final int arrayIndex = lastIndex.asInt();
                ((LzrArray) container).set(arrayIndex, value);
                return value;

            case Types.MAP:
                ((LzrMap) container).set(lastIndex, value);
                return value;
                
            case Types.CLASS:
                ((ClassInstanceValue) container).set(lastIndex, value);
                return value;
                
            default:
                throw new LzrException("TypeException","Array or map expected. Got " + container.type());
        }
    }
    
    public LzrValue getContainer() {
        LzrValue container = root.eval();
        final int last = indices.size() - 1;
        for (int i = 0; i < last; i++) {
            final LzrValue index = index(i);
            container = switch (container.type()) {
                case Types.ARRAY -> {
                    final int arrayIndex = index.asInt();
                    yield ((LzrArray) container).get(arrayIndex);
                }
                case Types.MAP -> ((LzrMap) container).get(index);
                default -> throw new LzrException("TypeException", "Array or map expected");
            };
        }
        return container;
    }
    
    public LzrValue lastIndex() {
        return index(indices.size() - 1);
    }
    
    private LzrValue index(int index) {
        return indices.get(index).eval();
    }
    
    public LzrMap consumeMap(LzrValue value) {
        if (value.type() != Types.MAP) {
            throw new LzrException("TypeException","Map expected");
        }
        return (LzrMap) value;
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
        return root.toString() + indices;
    }
}
