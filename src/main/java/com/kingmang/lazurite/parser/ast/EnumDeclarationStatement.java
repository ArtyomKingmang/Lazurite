package com.kingmang.lazurite.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class EnumDeclarationStatement implements Statement {

    public final String name;
    public final List<String> variants;

    public EnumDeclarationStatement(String name) {
        this.name = name;
        this.variants = new ArrayList<>();
    }

    public void addVariant(String variantName) {
        this.variants.add(variantName);
    }

    @Override
    public void execute() {
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> resultVisitor, T t) {
        return null;
    }
}
