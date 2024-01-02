package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.libraries.keyword;
import com.kingmang.lazurite.parser.AST.Arguments;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.UserDefinedFunction;


public final class FunctionDefineStatement implements Statement {
    
    public final String name;
    public final com.kingmang.lazurite.parser.AST.Arguments arguments;
    public final Statement body;
    
    public FunctionDefineStatement(String name, Arguments arguments, Statement body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute() {
        keyword.put(name, new UserDefinedFunction(arguments, body));
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
        if (body instanceof ReturnStatement) {
            return String.format("func %s%s = %s", name, arguments, ((ReturnStatement)body).expression);
        }
        return String.format("func %s%s %s", name, arguments, body);
    }
}
