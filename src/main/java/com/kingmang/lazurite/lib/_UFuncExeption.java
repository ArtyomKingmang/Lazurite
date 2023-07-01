package com.kingmang.lazurite.lib;

public final class _UFuncExeption extends RuntimeException {
    
    private final String functionName;

    public _UFuncExeption(String name) {
        super("Unknown function " + name);
        this.functionName = name;
    }

    public String getFunctionName() {
        return functionName;
    }
}
