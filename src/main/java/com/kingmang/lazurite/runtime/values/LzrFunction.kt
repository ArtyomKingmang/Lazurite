package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.throwTypeCastException
import java.util.*

class LzrFunction(
    val value: Function
) : LzrValue {

    companion object {
        @JvmField
        val EMPTY = LzrFunction { LzrNumber.ZERO }
    }

    override fun type(): Int {
        return Types.FUNCTION
    }

    override fun raw(): Any {
        return value
    }

    override fun asInt(): Int {
        throwTypeCastException("function", "integer")
    }

    override fun asNumber(): Double {
        throwTypeCastException("function", "number")
    }

    override fun asString(): String {
        return value.toString()
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }


    override fun hashCode(): Int {
        var hash = 7
        hash = 71 * hash + Objects.hashCode(this.value)
        return hash
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        other as LzrFunction
        return this.value == other.value
    }

    override fun compareTo(other: LzrValue): Int {
        return asString().compareTo(other.asString())
    }

    override fun toString(): String {
        return asString()
    }
}
