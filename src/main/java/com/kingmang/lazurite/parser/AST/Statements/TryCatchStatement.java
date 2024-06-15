package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrString;
import lombok.Getter;

public final class TryCatchStatement implements Statement{

    @Getter
    private final Statement tryStatement, catchStatement;

    public TryCatchStatement(Statement tryStatement, Statement catchStatement) {
        this.tryStatement = tryStatement;
        this.catchStatement = catchStatement;
    }

    @Override
    public void execute() {
        try{
            tryStatement.execute();
        }
        catch (LzrException ex){
            final LzrMap exInfo = new LzrMap(2);
            exInfo.set("type", new LzrString(ex.getType()));
            exInfo.set("text", new LzrString(ex.getText()));
            Variables.define("exception", exInfo);
            catchStatement.execute();
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
        return "try " + tryStatement + "catch " + catchStatement;
    }
}