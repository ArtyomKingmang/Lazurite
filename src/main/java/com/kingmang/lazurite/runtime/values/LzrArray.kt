package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Converters
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrArrayOrNull
import com.kingmang.lazurite.core.throwTypeCastException

open class LzrArray(private val elements: Array<LzrValue>, unit: Unit) : LzrValue, Iterable<LzrValue> {
    constructor(size: Int) :
            this(Array(size) { LzrNull }, Unit)
    constructor(size: Int, init: (index: Int) -> LzrValue) :
            this(Array(size, init), Unit)
    constructor(values: Array<LzrValue>) :
            this(values.copyOf(), Unit)
    constructor(values: List<LzrValue>) :
            this(values.toTypedArray(), Unit)
    constructor(array: LzrArray) :
            this(array.elements.copyOf(), Unit)

    open val copyElements: Array<LzrValue>
        get() = this.elements.copyOf()

    override fun type(): Int =
        Types.ARRAY

    open fun size(): Int =
        this.elements.size

    open fun isEmpty(): Boolean =
        this.size() == 0

    open fun add(value: LzrValue): LzrArray =
        add(this, value)

    open fun merge(array: LzrArray): LzrArray =
        merge(this, array)

    open operator fun get(index: Int): LzrValue =
        this.elements[index]

    operator fun get(index: LzrValue): LzrValue =
        when (index.asString()) {
            "length" -> LzrNumber.of(this.size())
            "isEmpty" -> Converters.voidToBoolean { this.size() == 0 }
            else -> this[index.asInt()]
        }

    open operator fun set(index: Int, value: LzrValue) {
        this.elements[index] = value
    }

    override fun raw(): Array<LzrValue> =
        this.elements

    override fun asInt(): Int =
        throwTypeCastException("array", "integer")

    override fun asNumber(): Double =
        throwTypeCastException("array", "number")

    override fun asString(): String =
        this.elements.contentToString()

    override fun asArray(): IntArray =
        IntArray(0)

    override fun iterator(): Iterator<LzrValue> =
        this.elements.iterator()

    override fun hashCode(): Int {
        var hash = 5
        hash = 79 * hash + this.elements.contentDeepHashCode()
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || this.javaClass != other.javaClass)
            return false
        other as LzrArray
        return elements.contentDeepEquals(other.elements)
    }

    override fun compareTo(other: LzrValue): Int {
        other.asLzrArrayOrNull()?.let {
            val lengthCompare = this.size().compareTo(it.size())
            if (lengthCompare != 0) {
                return lengthCompare
            }
        }
        return this.asString().compareTo(other.asString())
    }

    override fun toString(): String =
        this.asString()

    companion object {
        @JvmStatic
        fun of(vararg values: LzrValue): LzrArray =
            LzrArray(arrayOf(*values))

        @JvmStatic
        fun of(array: ByteArray): LzrArray =
            LzrArray(array.size) { LzrNumber.of(array[it].toInt()) }

        @JvmStatic
        fun of(array: Array<String>): LzrArray =
            LzrArray(array.size) { LzrString(array[it]) }

        @JvmStatic
        fun add(array: LzrArray, value: LzrValue): LzrArray {
            val last = array.elements.size
            return LzrArray(last + 1).apply {
                array.elements.copyInto(this.elements)
                this.elements[last] = value
            }
        }

        @JvmStatic
        fun merge(array1: LzrArray, array2: LzrArray): LzrArray {
            val length1 = array1.elements.size
            val length2 = array2.elements.size
            val length = length1 + length2
            val result = LzrArray(length)
            array1.elements.copyInto(result.elements, endIndex = length1)
            array2.elements.copyInto(result.elements, destinationOffset = length1, endIndex = length2)
            return result
        }

        fun joinToString(array: LzrArray, delimiter: String = "", prefix: String = "", suffix: String = ""): LzrString {
            val sb = StringBuilder()
            for (value in array) {
                if (sb.isNotEmpty())
                    sb.append(delimiter)
                else sb.append(prefix)
                sb.append(value.asString())
            }
            sb.append(suffix)
            return LzrString(sb.toString())
        }
    }
}
