package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.core.ClassDeclarations;
import com.kingmang.lazurite.parser.AST.Expressions.AssignmentExpression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public final class ClassDeclarationStatement implements Statement {
    
    public final String name;
    public final List<FunctionDefineStatement> methods;
    public final List<AssignmentExpression> fields;
    
    public ClassDeclarationStatement(String name) {
        this.name = name;
        methods = new ArrayList<>();
        fields = new ArrayList<>();
    }
    
    public void addField(AssignmentExpression expr) {
        fields.add(expr);
    }

    public void addMethod(FunctionDefineStatement statement) {
        methods.add(statement);
    }

    @Override
    public void execute() {
        ClassDeclarations.set(name, this);
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
        return String.format("class %s {\n  %s  %s}", name, fields, methods);
    }
}
