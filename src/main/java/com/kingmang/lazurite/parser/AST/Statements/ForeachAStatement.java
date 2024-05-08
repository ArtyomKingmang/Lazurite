package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import com.kingmang.lazurite.runtime.Types.LzrMap;
import com.kingmang.lazurite.runtime.Types.LzrString;
import lombok.AllArgsConstructor;


import java.util.Map;

@AllArgsConstructor
public final class ForeachAStatement extends InterruptableNode implements Statement {
    
    public final String variable;
    public final Expression container;
    public final Statement body;

    @Override
    public void execute() {
        super.interruptionCheck();
        final LzrValue previousVariableValue = Variables.isExists(variable) ? Variables.get(variable) : null;

        final LzrValue containerValue = container.eval();
        switch (containerValue.type()) {
            case Types.STRING:
                iterateString(containerValue.asString());
                break;
            case Types.ARRAY:
                iterateArray((LzrArray) containerValue);
                break;
            case Types.MAP:
                iterateMap((LzrMap) containerValue);
                break;
            default:
                throw new LZRException("TypeExeption","Cannot iterate " + Types.typeToString(containerValue.type()));
        }

        // Restore variables
        if (previousVariableValue != null) {
            Variables.set(variable, previousVariableValue);
        } else {
            Variables.remove(variable);
        }
    }

    private void iterateString(String str) {
        for (char ch : str.toCharArray()) {
            Variables.set(variable, new LzrString(String.valueOf(ch)));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }

    private void iterateArray(LzrArray containerValue) {
        for (LzrValue value : containerValue) {
            Variables.set(variable, value);
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }

    private void iterateMap(LzrMap containerValue) {
        for (Map.Entry<LzrValue, LzrValue> entry : containerValue) {
            Variables.set(variable, new LzrArray(new LzrValue[] {
                    entry.getKey(),
                    entry.getValue()
            }));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
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
        return String.format("for %s : %s %s", variable, container, body);
    }
}
