package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class AssertStatement extends InterruptableNode implements Statement {

    public final Expression expression;

    @Override
    public void execute() {
        super.interruptionCheck();
        int expr = expression.eval().asInt();
        boolean booleanExpr;
        if(expr == 1)booleanExpr = true;
        else booleanExpr = false;

        if (!booleanExpr) {
            throw new LzrException("AssertionError", "Assertion failed: " + expression);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        //return null;
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "assert " + expression;
    }
}
