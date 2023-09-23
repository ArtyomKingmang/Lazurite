package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import java.util.List;


public final class ContainerAccessExpression implements Expression, Accessible {
    
    public final Expression root;
    public final List<Expression> indices;
    private boolean rootIsVariable;

    public ContainerAccessExpression(String variable, List<Expression> indices) {
        this(new VariableExpression(variable), indices);
    }

    public ContainerAccessExpression(Expression root, List<Expression> indices) {
        rootIsVariable = root instanceof VariableExpression;
        this.root = root;
        this.indices = indices;
    }

    public boolean rootIsVariable() {
        return rootIsVariable;
    }

    public Expression getRoot() {
        return root;
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
                return ((LZRArray) container).get(lastIndex);

            case Types.MAP:
                return ((LZRMap) container).get(lastIndex);

            case Types.STRING:
                return ((LZRString) container).access(lastIndex);
                
            case Types.CLASS:
                return ((ClassInstanceValue) container).access(lastIndex);
                
            default:
                throw new LZRExeption("TypeExeption","Array or map expected. Got " + Types.typeToString(container.type()));
        }
    }

    @Override
    public Value set(Value value) {
        final Value container = getContainer();
        final Value lastIndex = lastIndex();
        switch (container.type()) {
            case Types.ARRAY:
                final int arrayIndex = lastIndex.asInt();
                ((LZRArray) container).set(arrayIndex, value);
                return value;

            case Types.MAP:
                ((LZRMap) container).set(lastIndex, value);
                return value;
                
            case Types.CLASS:
                ((ClassInstanceValue) container).set(lastIndex, value);
                return value;
                
            default:
                throw new LZRExeption("TypeExeption","Array or map expected. Got " + container.type());
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
                    container = ((LZRArray) container).get(arrayIndex);
                    break;
                    
                case Types.MAP:
                    container = ((LZRMap) container).get(index);
                    break;
                    
                default:
                    throw new LZRExeption("TypeExeption","Array or map expected");
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
    
    public LZRMap consumeMap(Value value) {
        if (value.type() != Types.MAP) {
            throw new LZRExeption("TypeExeption","Map expected");
        }
        return (LZRMap) value;
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
