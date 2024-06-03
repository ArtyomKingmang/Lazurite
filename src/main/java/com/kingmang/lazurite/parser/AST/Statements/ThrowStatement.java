package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ThrowStatement implements Statement {
    private String type;
    private Expression expr;


    @Override
    public void execute() {
        throw new LzrException(type, expr.eval().toString());
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