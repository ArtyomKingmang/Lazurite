package com.kingmang.lazurite.exceptions

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.runtime.values.LzrValue

class OperationIsNotSupportedException : LzrException {

    // [main constructors]
    constructor(operation: Any) : super("OperationIsNotSupportedException", "Operation $operation is not supported")
    constructor(operation: Any, message: String) : super("OperationIsNotSupportedException", "Operation $operation is not supported $message")


    // [unary operations]
    constructor(operation: Any, type: Int) : this(operation, "for ${Types.typeToString(type)}")

    constructor(operation: Any, value: LzrValue): this(operation, "for ${Types.typeToString(value)}")


    // [binary operations]
    constructor(operation: Any, type1: String, type2: String)
            : super("OperationIsNotSupportedException", "Operation $operation is not supported between $type1 and $type2")

    constructor(operation: Any, type1: Int, type2: Int)
            : this(operation, Types.typeToString(type1), Types.typeToString(type2))

    constructor(operation: Any, type1: String, type2: Int)
        : this(operation, type1, Types.typeToString(type2))

    constructor(operation: Any, type1: Int, type2: String)
        : this(operation, Types.typeToString(type1), type2)
}