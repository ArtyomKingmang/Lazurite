package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.throwTypeCastException
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue
import java.util.*

class ClassInstanceValue(
    private val className: String,
    private val thisMap: LzrMap,
    private val toString: UserDefinedFunction?
) : LzrValue {
    fun access(value: LzrValue): LzrValue =
        this.thisMap[value]
            ?: throw LzrException("RuntimeException", "Unknown member ${value.asString()} in class $className")

    fun set(key: LzrValue, value: LzrValue) {
        if (!this.thisMap.containsKey(key))
            throw LzrException("RuntimeException", "Unable to add new field ${key.asString()} to class $className")
        this.thisMap[key] = value
    }

    override fun raw(): Any? =
        null

    override fun asInt(): Int =
        throwTypeCastException("class", "integer")

    override fun asNumber(): Double =
        throwTypeCastException("class", "number")

    override fun asString(): String =
        this.toString?.execute()?.asString() ?: "${this.className}@${this.thisMap}"

    override fun asArray(): IntArray =
        IntArray(0)

    override fun type(): Int =
        Types.CLASS

    override fun hashCode(): Int {
        var hash = 5
        hash = 37 * hash + Objects.hash(className, thisMap)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this.javaClass != other.javaClass)
            return false
        other as ClassInstanceValue
        return this.className == other.className && (this.thisMap == other.thisMap)
    }

    override fun compareTo(other: LzrValue): Int =
        this.asString().compareTo(other.asString())

    override fun toString(): String =
        this.asString()
}
