package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.OperationIsNotSupportedException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ThrowStatement implements Statement {
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Expression expr;


    @Override
    public void execute() {
        throw new OperationIsNotSupportedException(type, expr.eval().toString());
    }

    @Override
    public String toString() {
        return "throw " + type + " " + expr;
    }


    @Override
    public void accept(Visitor visitor) {}

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}