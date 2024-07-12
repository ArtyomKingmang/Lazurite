package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*

fun LzrValue.asLzrNumber(lazyMessage: (() -> String)? = null): LzrNumber {
    return asLzrType(Types.NUMBER, lazyMessage)
}

fun LzrValue.asLzrString(lazyMessage: (() -> String)? = null): LzrString {
    return asLzrType(Types.STRING, lazyMessage)
}

fun LzrValue.asLzrArray(lazyMessage: (() -> String)? = null): LzrArray {
    return asLzrType(Types.ARRAY, lazyMessage)
}

fun LzrValue.asLzrMap(lazyMessage: (() -> String)? = null): LzrMap {
    return asLzrType(Types.MAP, lazyMessage)
}

fun LzrValue.asLzrFunction(lazyMessage: (() -> String)? = null): LzrFunction {
    return asLzrType(Types.FUNCTION, lazyMessage)
}

fun LzrValue.asLzrClass(lazyMessage: (() -> String)? = null): ClassInstanceValue {
    return asLzrType(Types.CLASS, lazyMessage)
}

inline fun <reified T : Any> LzrValue.asLzrType(targetType: Int, noinline lazyMessage: (() -> String)?): T {
    if (isLzrType(targetType)) return this as T
    if (lazyMessage == null) throwTypeCastException(type(), targetType)
    throwTypeException(lazyMessage.invoke())
}

fun throwTypeCastException(origin: Int, target: Int): Nothing {
    throwTypeCastException(Types.typeToString(origin), Types.typeToString(target))
}

fun throwTypeCastException(origin: String, target: String): Nothing {
    throwTypeException("Cannot cast $origin to $target")
}

fun throwTypeException(message: String): Nothing {
    throw LzrException("TypeException", message)
}