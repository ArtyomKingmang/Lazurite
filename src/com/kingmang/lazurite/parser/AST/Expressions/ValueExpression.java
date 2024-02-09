package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ValueExpression extends InterruptableNode implements Expression {
    
    public final Value value;
    
    public ValueExpression(Number value) {
        this.value = LzrNumber.of(value);
    }
    
    public ValueExpression(String value) {
        this.value = new LzrString(value);
    }
    
    public ValueExpression(Function value) {
        this.value = new LzrFunction(value);
    }

    @Override
    public Value eval() {
        super.interruptionCheck();
        return value;
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
        if (value.type() == Types.STRING) {
            return "\"" + value.asString() + "\"";
        }
        return value.toString();
    }
}
