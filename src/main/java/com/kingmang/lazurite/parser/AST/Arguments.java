package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Arguments implements Iterable<Argument> {
    
    private final List<Argument> arguments;
    @Getter
    private int requiredArgumentsCount;

    public Arguments() {
        arguments = new ArrayList<>();
        requiredArgumentsCount = 0;
    }
    public static void check(int expected, int got) {
        if (got != expected) throw new LZRException("ArgumentException ",String.format(
                "%d %s expected, got %d", expected, pluralize(expected), got));
    }

    private static String pluralize(int count) {
        return (count == 1) ? "argument" : "arguments";
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

    public int size() {
        return arguments.size();
    }

    @NotNull
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
