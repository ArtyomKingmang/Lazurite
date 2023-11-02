package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;


import java.util.Map;


public final class ForeachMStatement extends InterruptableNode implements Statement {
    
    public final String key, value;
    public final Expression container;
    public final Statement body;

    public ForeachMStatement(String key, String value, Expression container, Statement body) {
        this.key = key;
        this.value = value;
        this.container = container;
        this.body = body;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        final Value previousVariableValue1 = Variables.isExists(key) ? Variables.get(key) : null;
        final Value previousVariableValue2 = Variables.isExists(value) ? Variables.get(value) : null;

        final Value containerValue = container.eval();
        switch (containerValue.type()) {
            case Types.STRING:
                iterateString(containerValue.asString());
                break;
            case Types.ARRAY:
                iterateArray((LZRArray) containerValue);
                break;
            case Types.MAP:
                iterateMap((LZRMap) containerValue);
                break;
            default:
                throw new LZRException("TypeExeption","Cannot iterate " + Types.typeToString(containerValue.type()) + " as key, value pair");
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
            Variables.set(key, new LZRString(String.valueOf(ch)));
            Variables.set(value, LZRNumber.of(ch));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }

    private void iterateArray(LZRArray containerValue) {
        int index = 0;
        for (Value v : containerValue) {
            Variables.set(key, v);
            Variables.set(value, LZRNumber.of(index++));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }

    private void iterateMap(LZRMap containerValue) {
        for (Map.Entry<Value, Value> entry : containerValue) {
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
