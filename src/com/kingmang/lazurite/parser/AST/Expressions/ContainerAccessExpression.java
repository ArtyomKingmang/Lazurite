package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.parser.AST.Accessible;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import lombok.Getter;


import java.util.List;


public final class ContainerAccessExpression implements Expression, Accessible {

    @Getter
    public final Expression root;
    @Getter
    public final List<Expression> indices;
    @Getter
    private boolean rootIsVariable;

    public ContainerAccessExpression(String variable, List<Expression> indices) {
        this(new VariableExpression(variable), indices);
    }

    public ContainerAccessExpression(Expression root, List<Expression> indices) {
        rootIsVariable = root instanceof VariableExpression;
        this.root = root;
        this.indices = indices;
    }


    @Override
    public Value eval() {
        return get();
    }
    
    @Override
    public Value get() {
        final Value container = getContainer();
        final Value lastIndex = lastIndex();
        switch (container.type()) {
            case Types.ARRAY:
                return ((LzrArray) container).get(lastIndex);

            case Types.MAP:
                return ((LzrMap) container).get(lastIndex);

            case Types.STRING:
                return ((LzrString) container).access(lastIndex);
                
            case Types.CLASS:
                return ((ClassInstanceValue) container).access(lastIndex);
                
            default:
                throw new LZRException("TypeExeption","Array or map expected. Got " + Types.typeToString(container.type()));
        }
    }

    @Override
    public Value set(Value value) {
        final Value container = getContainer();
        final Value lastIndex = lastIndex();
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
                throw new LZRException("TypeExeption","Array or map expected. Got " + container.type());
        }
    }
    
    public Value getContainer() {
        Value container = root.eval();
        final int last = indices.size() - 1;
        for (int i = 0; i < last; i++) {
            final Value index = index(i);
            switch (container.type()) {
                case Types.ARRAY:
                    final int arrayIndex = index.asInt();
                    container = ((LzrArray) container).get(arrayIndex);
                    break;
                    
                case Types.MAP:
                    container = ((LzrMap) container).get(index);
                    break;
                    
                default:
                    throw new LZRException("TypeExeption","Array or map expected");
            }
        }
        return container;
    }
    
    public Value lastIndex() {
        return index(indices.size() - 1);
    }
    
    private Value index(int index) {
        return indices.get(index).eval();
    }
    
    public LzrMap consumeMap(Value value) {
        if (value.type() != Types.MAP) {
            throw new LZRException("TypeExeption","Map expected");
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
