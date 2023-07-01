package com.kingmang.lazurite.parser.ast;

public final class Argument {

    private final String name;
    private final Expression valueExpr;

    public Argument(String name) {
        this(name, null);
    }

    public Argument(String name, Expression valueExpr) {
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
