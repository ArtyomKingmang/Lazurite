package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.runtime.UserDefinedFunction;


public final class FunctionDefineStatement implements Statement {
    
    public final String name;
    public final Arguments arguments;
    public final Statement body;
    
    public FunctionDefineStatement(String name, Arguments arguments, Statement body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute() {
        KEYWORD.put(name, new UserDefinedFunction(arguments, body));
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
