@file:Suppress("NOTHING_TO_INLINE")
package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*

inline fun LzrValue.asLzrNumber(noinline lazyMessage: (() -> String)?): LzrNumber =
    asLzrType(Types.NUMBER, lazyMessage)

inline fun LzrValue.asLzrNumber(): LzrNumber =
    asLzrType(Types.NUMBER)

inline fun LzrValue.asLzrString(noinline lazyMessage: (() -> String)?): LzrString =
    asLzrType(Types.STRING, lazyMessage)

inline fun LzrValue.asLzrString(): LzrString =
    asLzrType(Types.STRING)

inline fun LzrValue.asLzrArray(noinline lazyMessage: (() -> String)?): LzrArray =
    asLzrType(Types.ARRAY, lazyMessage)

inline fun LzrValue.asLzrArray(): LzrArray =
    asLzrType(Types.ARRAY)

inline fun LzrValue.asLzrMap(noinline lazyMessage: (() -> String)?): LzrMap =
    asLzrType(Types.MAP, lazyMessage)

inline fun LzrValue.asLzrMap(): LzrMap =
    asLzrType(Types.MAP)

inline fun LzrValue.asLzrFunction(noinline lazyMessage: (() -> String)? = null): LzrFunction =
    asLzrType(Types.FUNCTION, lazyMessage)

inline fun LzrValue.asLzrFunction(): LzrFunction =
    asLzrType(Types.FUNCTION)

inline fun LzrValue.asLzrClass(noinline lazyMessage: (() -> String)? = null): ClassInstanceValue =
    asLzrType(Types.CLASS, lazyMessage)

inline fun LzrValue.asLzrClass(): ClassInstanceValue =
    asLzrType(Types.CLASS)

inline fun <reified T : Any> LzrValue.asLzrType(targetType: Int, noinline lazyMessage: (() -> String)?): T {
    if (isLzrType(targetType))
        return this as T
    lazyMessage ?: throwTypeCastException(type(), targetType)
    throwTypeException(lazyMessage.invoke())
}

inline fun <reified T : Any> LzrValue.asLzrType(targetType: Int): T {
    if (isLzrType(targetType))
        return this as T
    throwTypeCastException(type(), targetType)
}


fun throwTypeCastException(origin: Int, target: Int): Nothing =
    throwTypeCastException(Types.typeToString(origin), Types.typeToString(target))


fun throwTypeCastException(origin: String, target: String): Nothing =
    throwTypeException("Cannot cast $origin to $target")

fun throwTypeException(message: String): Nothing =
    throw LzrException("TypeException", message)