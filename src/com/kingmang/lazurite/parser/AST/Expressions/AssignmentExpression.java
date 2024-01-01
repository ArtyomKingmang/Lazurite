package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.parser.AST.Accessible;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Value;


public final class AssignmentExpression extends com.kingmang.lazurite.parser.AST.InterruptableNode implements com.kingmang.lazurite.parser.AST.Expressions.Expression, Statement {

    public final com.kingmang.lazurite.parser.AST.Accessible target;
    public final com.kingmang.lazurite.parser.AST.Expressions.BinaryExpression.Operator operation;
    public final com.kingmang.lazurite.parser.AST.Expressions.Expression expression;
    
    public AssignmentExpression(com.kingmang.lazurite.parser.AST.Expressions.BinaryExpression.Operator operation, Accessible target, com.kingmang.lazurite.parser.AST.Expressions.Expression expr) {
        this.operation = operation;
        this.target = target;
        this.expression = expr;
    }
    
    @Override
    public void execute() {
        eval();
    }

    @Override
    public Value eval() {
        super.interruptionCheck();
        if (operation == null) {
            // Simple assignment
            return target.set(expression.eval());
        }
        final com.kingmang.lazurite.parser.AST.Expressions.Expression expr1 = new ValueExpression(target.get());
        final Expression expr2 = new ValueExpression(expression.eval());
        return target.set(new BinaryExpression(operation, expr1, expr2).eval());
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
        final String op = (operation == null) ? "" : operation.toString();
        return String.format("%s %s= %s", target, op, expression);
    }
}
