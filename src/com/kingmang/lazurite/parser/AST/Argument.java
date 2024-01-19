package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;

public final class Argument {

    private final String name;
    private final com.kingmang.lazurite.parser.AST.Expressions.Expression valueExpr;

    public Argument(String name) {
        this(name, null);
    }

    public Argument(String name, com.kingmang.lazurite.parser.AST.Expressions.Expression valueExpr) {
        this.name = name;
        this.valueExpr = valueExpr;
    }

    public String getName() {
        return name;
    }

    public Expression getValueExpr() {
        return valueExpr;
    }

    @Override
    public String toString() {
        return name + (valueExpr == null ? "" : " = " + valueExpr);
    }
}
