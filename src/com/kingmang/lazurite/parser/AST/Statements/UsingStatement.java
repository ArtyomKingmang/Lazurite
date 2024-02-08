package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.parser.AST.Expressions.ArrayExpression;
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.libraries.Library;

import java.lang.reflect.Method;


public final class UsingStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "com.kingmang.lazurite.libraries.%s.%s";

    
    public final Expression expression;
    
    public UsingStatement(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void execute() {
        super.interruptionCheck();
        final Value value = expression.eval();
        try {
            loadModule(value.asString());
        }catch (Exception e){
            throw new LZRException("Type","Array or string required in 'use' statement, " +
                    "got " + Types.typeToString(value.type()) + " " + value);
        }
    }

    private void loadModule(String name) {
        try {
            final Library module = (Library) Class.forName(String.format(PACKAGE, name, name)).newInstance();
            module.init();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load module " + name, ex);
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
        return "using " + expression;
    }
}
