package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.core.ClassDeclarations;
import com.kingmang.lazurite.core.Instantiable;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Statements.ClassDeclarationStatement;
import com.kingmang.lazurite.parser.AST.Statements.FunctionDefineStatement;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.ClassInstanceBuilder;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.Iterator;
import java.util.List;

public record ObjectCreationExpression(String className, List<Expression> constructorArguments) implements Expression {

    @Override
    public LzrValue eval() {
        final ClassDeclarationStatement cd = ClassDeclarations.get(className);
        if (cd == null) {
            // Is Instantiable?
            if (Variables.isExists(className)) {
                final LzrValue variable = Variables.get(className);
                if (variable instanceof Instantiable) {
                    return ((Instantiable) variable).newInstance(ctorArgs());
                }
            }
            throw new LzrException("UnknownClassException ", "Unknown class " + className);
        }


        final ClassInstanceBuilder builder = new ClassInstanceBuilder(className);
        for (AssignmentExpression f : cd.fields) {

            final String fieldName = ((VariableExpression) f.target).name;
            builder.addField(fieldName, f.eval());
        }
        for (FunctionDefineStatement m : cd.methods) {
            builder.addMethod(m.name(), m.arguments(), m.body());
        }
        return builder.build(ctorArgs());
    }

    private LzrValue[] ctorArgs() {
        final int argsSize = constructorArguments.size();
        final LzrValue[] ctorArgs = new LzrValue[argsSize];
        for (int i = 0; i < argsSize; i++) {
            ctorArgs[i] = constructorArguments.get(i).eval();
        }
        return ctorArgs;
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
        final StringBuilder sb = new StringBuilder();
        sb.append("new ").append(className).append(' ');
        final Iterator<Expression> it = constructorArguments.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(", ").append(it.next());
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
