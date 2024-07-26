@file:Suppress("NOTHING_TO_INLINE")
package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.values.LzrValue

inline fun LzrValue.isLzrNumber(): Boolean =
    isLzrType(Types.NUMBER)

inline fun LzrValue.isLzrString(): Boolean =
    isLzrType(Types.STRING)

inline fun LzrValue.isLzrArray(): Boolean =
    isLzrType(Types.ARRAY)

inline fun LzrValue.isLzrMap(): Boolean =
    isLzrType(Types.MAP)

inline fun LzrValue.isLzrFunction(): Boolean =
    isLzrType(Types.FUNCTION)

inline fun LzrValue.isLzrClass(): Boolean =
    isLzrType(Types.CLASS)

inline fun LzrValue.isLzrType(targetType: Int): Boolean =
    type() == targetType