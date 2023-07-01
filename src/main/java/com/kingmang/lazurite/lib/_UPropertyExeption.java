package com.kingmang.lazurite.lib;

public final class _UPropertyExeption extends RuntimeException {

    private final String propertyName;

    public _UPropertyExeption(String name) {
        super("Unknown property " + name);
        this.propertyName = name;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
