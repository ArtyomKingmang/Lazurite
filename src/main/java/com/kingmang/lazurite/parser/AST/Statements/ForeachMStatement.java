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
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.runtime.Types.LzrString;
import lombok.AllArgsConstructor;


import java.util.Map;

@AllArgsConstructor
public final class ForeachMStatement extends InterruptableNode implements Statement {
    
    public final String key, value;
    public final Expression container;
    public final Statement body;

    @Override
    public void execute() {
        super.interruptionCheck();
        final LzrValue previousVariableValue1 = Variables.isExists(key) ? Variables.get(key) : null;
        final LzrValue previousVariableValue2 = Variables.isExists(value) ? Variables.get(value) : null;

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
                throw new LZRException("TypeException","Cannot iterate " + Types.typeToString(containerValue.type()) + " as key, value pair");
        }

        // Restore variables
        if (previousVariableValue1 != null) {
            Variables.set(key, previousVariableValue1);
        } else {
            Variables.remove(key);
        }
        if (previousVariableValue2 != null) {
            Variables.set(value, previousVariableValue2);
        } else {
            Variables.remove(value);
        }
    }

    private void iterateString(String str) {
        for (char ch : str.toCharArray()) {
            Variables.set(key, new LzrString(String.valueOf(ch)));
            Variables.set(value, LzrNumber.of(ch));
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
        int index = 0;
        for (LzrValue v : containerValue) {
            Variables.set(key, v);
            Variables.set(value, LzrNumber.of(index++));
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
            Variables.set(key, entry.getKey());
            Variables.set(value, entry.getValue());
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
        return String.format("for %s, %s : %s %s", key, value, container, body);
    }
}
