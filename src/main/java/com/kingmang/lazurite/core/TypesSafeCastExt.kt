package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*

fun LzrValue.asLzrNumberOrNull(): LzrNumber? {
    return asLzrTypeOrNull(Types.NUMBER)
}

fun LzrValue.asLzrStringOrNull(): LzrString? {
    return asLzrTypeOrNull(Types.STRING)
}

fun LzrValue.asLzrArrayOrNull(): LzrArray? {
    return asLzrTypeOrNull(Types.ARRAY)
}

fun LzrValue.asLzrMapOrNull(): LzrMap? {
    return asLzrTypeOrNull(Types.MAP)
}

fun LzrValue.asLzrFunctionOrNull(): LzrFunction? {
    return asLzrTypeOrNull(Types.FUNCTION)
}

fun LzrValue.asLzrClassOrNull(): ClassInstanceValue? {
    return asLzrTypeOrNull(Types.CLASS)
}

inline fun <reified T : Any> LzrValue.asLzrTypeOrNull(targetType: Int): T? {
    if (isLzrType(targetType)) return this as T
    return null
}