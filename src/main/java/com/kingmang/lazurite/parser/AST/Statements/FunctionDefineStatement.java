package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.parser.AST.Arguments;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import lombok.AllArgsConstructor;

public record FunctionDefineStatement(String name, Arguments arguments, Statement body) implements Statement {

    @Override
    public void execute() {
        Keyword.put(name, new UserDefinedFunction(arguments, body));
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
            return String.format("func %s%s = %s", name, arguments, ((ReturnStatement) body).expression);
        }
        return String.format("func %s%s %s", name, arguments, body);
    }
}
