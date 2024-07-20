package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrNumberOrNull


class LzrNumber(private val value: Number) : LzrValue {
    override fun type(): Int =
        Types.NUMBER

    override fun raw(): Number =
        this.value

    fun asBoolean(): Boolean =
        this.value.toInt() != 0

    fun asByte(): Byte =
        this.value.toByte()

    fun asShort(): Short =
        this.value.toShort()

    override fun asInt(): Int =
        this.value.toInt()

    fun asLong(): Long =
        this.value.toLong()

    fun asFloat(): Float =
        this.value.toFloat()

    fun asDouble(): Double =
        this.value.toDouble()

    override fun asNumber(): Double =
        this.value.toDouble()

    override fun asString(): String =
        this.value.toString()

    override fun asArray(): IntArray =
        IntArray(0)

    override fun hashCode(): Int {
        var hash = 3
        hash = 71 * hash + this.value.hashCode()
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || this.javaClass != other.javaClass)
            return false
        val otherValue = (other as LzrNumber).value
        return when {
            this.value is Double || otherValue is Double -> this.value.toDouble().compareTo(otherValue.toDouble()) == 0
            this.value is Float || otherValue is Float -> this.value.toFloat().compareTo(otherValue.toFloat()) == 0
            this.value is Long || otherValue is Long -> this.value.toLong() == otherValue.toLong()
            else -> this.value.toInt() == otherValue.toInt()
        }
    }

    override fun compareTo(other: LzrValue): Int =
        other.asLzrNumberOrNull()?.let {
            val otherValue = it.value
            when {
                this.value is Double || otherValue is Double -> this.value.toDouble().compareTo(otherValue.toDouble())
                this.value is Float || otherValue is Float -> this.value.toFloat().compareTo(otherValue.toFloat())
                this.value is Long || otherValue is Long -> this.value.toLong().compareTo(otherValue.toLong())
                else -> this.value.toInt().compareTo(otherValue.toInt())
            }
        } ?: this.asString().compareTo(other.asString())

    override fun toString(): String =
        this.asString()

    companion object {
        private const val CACHE_MIN = -128
        private const val CACHE_MAX = 127
        private const val ZERO_INDEX = -CACHE_MIN

        private val NUMBER_CACHE = Array(CACHE_MAX - CACHE_MIN + 1) { LzrNumber(CACHE_MIN + it) }

        @JvmField
        val MINUS_ONE = NUMBER_CACHE[ZERO_INDEX - 1]
        @JvmField
        val ZERO = NUMBER_CACHE[ZERO_INDEX]
        @JvmField
        val ONE = NUMBER_CACHE[ZERO_INDEX + 1]

        @JvmStatic
        fun fromBoolean(b: Boolean): LzrNumber =
            if (b) ONE else ZERO

        @JvmStatic
        fun of(value: Int): LzrNumber =
            if (value in CACHE_MIN..CACHE_MAX)
                NUMBER_CACHE[ZERO_INDEX + value]
            else LzrNumber(value)

        @JvmStatic
        fun of(value: Number): LzrNumber =
            LzrNumber(value)
    }
}
