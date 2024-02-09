package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import lombok.Getter;

public final class Argument {

    @Getter
    private final String name;
    @Getter
    private final Expression valueExpr;

    public Argument(String name) {
        this(name, null);
    }

    public Argument(String name, Expression valueExpr) {
        this.name = name;
        this.valueExpr = valueExpr;
    }
    @Override
    public String toString() {
        return name + (valueExpr == null ? "" : " = " + valueExpr);
    }
}
