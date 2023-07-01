package com.kingmang.lazurite.parser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Arguments implements Iterable<Argument> {
    
    private final List<Argument> arguments;
    private int requiredArgumentsCount;

    public Arguments() {
        arguments = new ArrayList<>();
        requiredArgumentsCount = 0;
    }
    
    public void addRequired(String name) {
        arguments.add(new Argument(name));
        requiredArgumentsCount++;
    }
    
    public void addOptional(String name, Expression expr) {
        arguments.add(new Argument(name, expr));
    }

    public Argument get(int index) {
        return arguments.get(index);
    }
    
    public int getRequiredArgumentsCount() {
        return requiredArgumentsCount;
    }

    public int size() {
        return arguments.size();
    }

    @Override
    public Iterator<Argument> iterator() {
        return arguments.iterator();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append('(');
        final Iterator<Argument> it = arguments.iterator();
        if (it.hasNext()) {
            result.append(it.next());
            while (it.hasNext()) {
                result.append(", ").append(it.next());
            }
        }
        result.append(')');
        return result.toString();
    }
}
