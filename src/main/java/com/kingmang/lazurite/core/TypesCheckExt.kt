package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.values.LzrValue


fun LzrValue.isLzrNumber(): Boolean {
    return isLzrType(Types.NUMBER)
}

fun LzrValue.isLzrString(): Boolean {
    return isLzrType(Types.STRING)
}

fun LzrValue.isLzrArray(): Boolean {
    return isLzrType(Types.ARRAY)
}

fun LzrValue.isLzrMap(): Boolean {
    return isLzrType(Types.MAP)
}

fun LzrValue.isLzrFunction(): Boolean {
    return isLzrType(Types.FUNCTION)
}

fun LzrValue.isLzrClass(): Boolean {
    return isLzrType(Types.CLASS)
}

inline fun LzrValue.isLzrType(targetType: Int): Boolean = type() == targetType