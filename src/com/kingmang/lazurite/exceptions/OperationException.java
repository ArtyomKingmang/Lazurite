package com.kingmang.lazurite.exceptions;

public final class OperationException extends RuntimeException {

    public OperationException(Object operation) {
        super("Operation " + operation + " is not supported");
    }

    public OperationException(Object operation, String message) {
        super("Operation " + operation + " is not supported " + message);
    }
}