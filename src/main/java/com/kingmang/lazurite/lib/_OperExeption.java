package com.kingmang.lazurite.lib;

public final class _OperExeption extends RuntimeException {

    public _OperExeption(Object operation) {
        super("Operation " + operation + " is not supported");
    }
    
    public _OperExeption(Object operation, String message) {
        super("Operation " + operation + " is not supported " + message);
    }
}
