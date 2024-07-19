package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.core.CallStack;
import com.kingmang.lazurite.exceptions.FileInfo;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.exceptions.LzrTracedException;
import com.kingmang.lazurite.exceptions.LzrTracedException.TraceInfo;
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
    private FileInfo file;

    @Override
    public void execute() {
        throw new LzrTracedException(type, expr.eval().toString(), new TraceInfo(file, CallStack.getCalls()));
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