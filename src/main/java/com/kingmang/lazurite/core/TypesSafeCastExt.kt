package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*

fun LzrValue.asLzrNumberOrNull(): LzrNumber? =
    asLzrTypeOrNull(Types.NUMBER)

fun LzrValue.asLzrStringOrNull(): LzrString? =
    asLzrTypeOrNull(Types.STRING)

fun LzrValue.asLzrArrayOrNull(): LzrArray? =
    asLzrTypeOrNull(Types.ARRAY)

fun LzrValue.asLzrMapOrNull(): LzrMap? =
    asLzrTypeOrNull(Types.MAP)

fun LzrValue.asLzrFunctionOrNull(): LzrFunction? =
    asLzrTypeOrNull(Types.FUNCTION)

fun LzrValue.asLzrClassOrNull(): ClassInstanceValue? =
    asLzrTypeOrNull(Types.CLASS)

inline fun <reified T : Any> LzrValue.asLzrTypeOrNull(targetType: Int): T? {
    if (isLzrType(targetType))
        return this as T
    return null
}