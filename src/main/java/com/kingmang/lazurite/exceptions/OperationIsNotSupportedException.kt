package com.kingmang.lazurite.exceptions;

public final class OperationIsNotSupportedException extends LzrException {

    public OperationIsNotSupportedException(Object operation) {
        super("OperationIsNotSupportedException: ", "Operation " + operation + " is not supported");
        //PrettyException.message(true,"OperationIsNotSupportedException","Operation " + operation + " is not supported");
    }

    public OperationIsNotSupportedException(Object operation, String message) {
        super("OperationIsNotSupportedException: ","Operation " + operation + " is not supported " + message);
        //PrettyException.message(true,"OperationIsNotSupportedException", "Operation " + operation + " is not supported " + message);
    }

}