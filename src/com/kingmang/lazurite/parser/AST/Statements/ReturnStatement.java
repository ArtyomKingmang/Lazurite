package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Value;


public final class ReturnStatement extends RuntimeException implements Statement {

    public final Expression expression;
    private Value result;
    
    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }
    
    public Value getResult() {
        return result;
    }
    
    @Override
    public void execute() {
        result = expression.eval();
        throw this;
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
        return "return " + expression;
    }
}
