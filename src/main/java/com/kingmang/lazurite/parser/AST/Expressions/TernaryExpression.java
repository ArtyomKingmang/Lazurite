package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class TernaryExpression implements Expression {
    public final Expression condition;
    public final Expression trueExpr, falseExpr;

    @Override
    public LzrValue eval() {
        if (condition.eval().asInt() != 0) {
            return trueExpr.eval();
        } else {
            return falseExpr.eval();
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
        return String.format("%s ? %s : %s", condition, trueExpr, falseExpr);
    }
}
