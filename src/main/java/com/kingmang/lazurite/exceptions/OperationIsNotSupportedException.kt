package com.kingmang.lazurite.exceptions

class OperationIsNotSupportedException : LzrException {
    constructor(operation: Any) : super("OperationIsNotSupportedException: ", "Operation $operation is not supported")
    constructor(operation: Any, message: String) : super("OperationIsNotSupportedException: ", "Operation $operation is not supported $message")
}