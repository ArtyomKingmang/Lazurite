package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrNumberOrNull


class LzrNumber(
    private val value: Number
) : LzrValue {

    companion object {
        private const val CACHE_MIN = -128
        private const val CACHE_MAX = 127
        private const val ZERO_INDEX = -CACHE_MIN

        private val NUMBER_CACHE = Array(CACHE_MAX - CACHE_MIN + 1) {
            LzrNumber(CACHE_MIN + it)
        }

        @JvmField
        val MINUS_ONE = NUMBER_CACHE[ZERO_INDEX - 1]

        @JvmField
        val ZERO = NUMBER_CACHE[ZERO_INDEX]

        @JvmField
        val ONE = NUMBER_CACHE[ZERO_INDEX + 1]

        @JvmStatic
        fun fromBoolean(b: Boolean): LzrNumber {
            return if (b) ONE else ZERO
        }

        @JvmStatic
        fun of(value: Int): LzrNumber {
            if (value in CACHE_MIN..CACHE_MAX) {
                return NUMBER_CACHE[ZERO_INDEX + value]
            }
            return LzrNumber(value)
        }

        @JvmStatic
        fun of(value: Number): LzrNumber {
            return LzrNumber(value)
        }
    }

    override fun type(): Int {
        return Types.NUMBER
    }

    override fun raw(): Number {
        return value
    }

    fun asBoolean(): Boolean {
        return value.toInt() != 0
    }

    fun asByte(): Byte {
        return value.toByte()
    }

    fun asShort(): Short {
        return value.toShort()
    }

    override fun asInt(): Int {
        return value.toInt()
    }

    fun asLong(): Long {
        return value.toLong()
    }

    fun asFloat(): Float {
        return value.toFloat()
    }

    fun asDouble(): Double {
        return value.toDouble()
    }

    override fun asNumber(): Double {
        return value.toDouble()
    }

    override fun asString(): String {
        return value.toString()
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun hashCode(): Int {
        var hash = 3
        hash = 71 * hash + value.hashCode()
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val otherValue = (other as LzrNumber).value
        if (value is Double || otherValue is Double) {
            return value.toDouble().compareTo(otherValue.toDouble()) == 0
        }
        if (value is Float || otherValue is Float) {
            return value.toFloat().compareTo(otherValue.toFloat()) == 0
        }
        if (value is Long || otherValue is Long) {
            return value.toLong() == otherValue.toLong()
        }
        return value.toInt() == otherValue.toInt()
    }

    override fun compareTo(other: LzrValue): Int {
        return other.asLzrNumberOrNull()
            ?.let {
                val otherValue = it.value
                when {
                    value is Double || otherValue is Double -> value.toDouble().compareTo(otherValue.toDouble())
                    value is Float || otherValue is Float -> value.toFloat().compareTo(otherValue.toFloat())
                    value is Long || otherValue is Long -> value.toLong().compareTo(otherValue.toLong())
                    else -> value.toInt().compareTo(otherValue.toInt())
                }
            }
            ?: asString().compareTo(other.asString())
    }

    override fun toString(): String {
        return asString()
    }
}
