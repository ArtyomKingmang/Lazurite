package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Argument {

    private final String name;
    private final Expression valueExpr;

    public Argument(String name) {
        this(name, null);
    }
    @Override
    public String toString() {
        return name + (valueExpr == null ? "" : " = " + valueExpr);
    }
}
