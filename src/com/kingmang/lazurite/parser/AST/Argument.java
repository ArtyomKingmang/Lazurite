package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Argument {

    @Getter
    private final String name;
    @Getter
    private final Expression valueExpr;

    public Argument(String name) {
        this(name, null);
    }
    @Override
    public String toString() {
        return name + (valueExpr == null ? "" : " = " + valueExpr);
    }
}
