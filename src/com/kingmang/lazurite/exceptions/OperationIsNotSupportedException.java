package com.kingmang.lazurite.exceptions;

public final class OperationIsNotSupportedException extends RuntimeException {

    public OperationIsNotSupportedException(Object operation) {
        super("Operation " + operation + " is not supported");
        PrettyException.message("OperationIsNotSupportedException","Operation " + operation + " is not supported");
    }

    public OperationIsNotSupportedException(Object operation, String message) {
        super("Operation " + operation + " is not supported " + message);
        PrettyException.message("OperationIsNotSupportedException", "Operation " + operation + " is not supported " + message);
    }

}