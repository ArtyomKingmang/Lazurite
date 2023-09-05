package com.kingmang.lazurite.LZREx;

public final class OExeption extends RuntimeException {

    public OExeption(Object operation) {
        super("Operation " + operation + " is not supported");
    }

    public OExeption(Object operation, String message) {
        super("Operation " + operation + " is not supported " + message);
    }
}