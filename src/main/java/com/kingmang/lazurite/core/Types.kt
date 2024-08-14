package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.values.LzrValue
import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
object Types {
    const val OBJECT: Int = 0
    const val NUMBER: Int = 1
    const val STRING: Int = 2
    const val ARRAY: Int = 3
    const val MAP: Int = 4
    const val FUNCTION: Int = 5
    const val CLASS: Int = 6

    private const val FIRST = OBJECT
    private const val LAST = CLASS
    val NAMES: Array<String> = arrayOf("object", "number", "string", "array", "map", "function", "class")

    @JvmStatic
    fun typeToString(type: Int): String =
        if (type in FIRST..LAST)
            NAMES[type]
        else "unknown ($type)"

    fun typeToString(value: LzrValue): String =
        this.typeToString(value.type())
}
