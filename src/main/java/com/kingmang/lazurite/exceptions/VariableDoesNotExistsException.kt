package com.kingmang.lazurite.exceptions

class VariableDoesNotExistsException(val variable: String) : LzrException("VariableDoesNotExistsException", "Variable: \"$variable\" does not exists")