package com.kingmang.lazurite.lib;

public final class _UClassExeption extends RuntimeException {
    
    private final String className;

    public _UClassExeption(String name) {
        super("Unknown class " + name);
        this.className = name;
    }

    public String getClassName() {
        return className;
    }
}
