package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.parser.AST.Arguments;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.UserDefinedFunction;

public class FunctionalDefineStatement implements Statement {
    private final String name;
    private final Arguments argNames;
    private final Statement body;

    public FunctionalDefineStatement(String name, Arguments argNames, Statement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Keyword.put(name, new UserDefinedFunction(argNames, body));
    }

    @Override
    public String toString() {
        return "fun (" + argNames.toString() + ") " + body.toString();
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}
