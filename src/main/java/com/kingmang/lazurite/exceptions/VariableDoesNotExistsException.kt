package com.kingmang.lazurite.exceptions

class VariableDoesNotExistsException : LzrException {
    val variable: String

    constructor(variable: String) : super("VariableDoesNotExistsException", "Variable \"$variable\" does not exists") {
        this.variable = variable
    }

    constructor(variable: String, message: String) : super("VariableDoesNotExistsException", "Variable \"$variable\" does not exists. $message") {
        this.variable = variable
    }
}