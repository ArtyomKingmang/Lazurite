package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.parser.pars.Console;

import java.util.ArrayList;
import java.util.List;


public final class MStatement extends InterruptableNode implements Statement {
    
    public final List<Statement> statements;

    public MStatement() {
        statements = new ArrayList<>();
    }
    
    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        for (Statement statement : statements) {
            statement.execute();
        }
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
        final StringBuilder result = new StringBuilder();
        for (Statement statement : statements) {
            result.append(statement.toString()).append(Console.newline());
        }
        return result.toString();
    }
}
