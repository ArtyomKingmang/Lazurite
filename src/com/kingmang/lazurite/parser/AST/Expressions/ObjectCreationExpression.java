package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.parser.AST.Statements.ClassDeclarationStatement;
import com.kingmang.lazurite.parser.AST.Statements.FunctionDefineStatement;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.ClassInstanceValue;
import com.kingmang.lazurite.runtime.ClassMethod;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public final class ObjectCreationExpression implements Expression {
    
    public final String className;
    public final List<Expression> constructorArguments;
    
    @Override
    public Value eval() {
        final ClassDeclarationStatement cd = ClassDeclarations.get(className);
        if (cd == null) {
            // Is Instantiable?
            if (Variables.isExists(className)) {
                final Value variable = Variables.get(className);
                if (variable instanceof Instantiable) {
                    return ((Instantiable) variable).newInstance(ctorArgs());
                }
            }
            throw new LZRException("UnknownClassException ","Unknown class " + className);
        }
        

        final ClassInstanceValue instance = new ClassInstanceValue(className);
        for (AssignmentExpression f : cd.fields) {

            final String fieldName = ((VariableExpression) f.target).name;
            instance.addField(fieldName, f.eval());
        }
        for (FunctionDefineStatement m : cd.methods) {
            instance.addMethod(m.name, new ClassMethod(m.arguments, m.body, instance));
        }
        

        instance.callConstructor(ctorArgs());
        return instance;
    }
    
    private Value[] ctorArgs() {
        final int argsSize = constructorArguments.size();
        final Value[] ctorArgs = new Value[argsSize];
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
