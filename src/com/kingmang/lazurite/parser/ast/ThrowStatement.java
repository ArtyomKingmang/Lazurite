package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.LZREx.OperationException;

public class ThrowStatement implements Statement{

    private String type;
    private Expression expr;

    public ThrowStatement(String type, Expression expr) {
        this.type = type;
        this.expr = expr;
    }


    @Override
    public void execute() {
        throw new OperationException(type, expr.eval().toString());
    }

    @Override
    public String toString() {
        return "throw " + type + " " + expr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}