package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.LzrValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ExprStatement extends InterruptableNode implements Expression, Statement {
    
    public final Expression expr;

    @Override
    public void execute() {
        super.interruptionCheck();
        expr.eval();
    }
    
    @Override
    public LzrValue eval() {
        return expr.eval();
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
        return expr.toString();
    }
}
