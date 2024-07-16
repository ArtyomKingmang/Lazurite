package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Converters
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrArrayOrNull
import com.kingmang.lazurite.core.throwTypeCastException

open class LzrArray : LzrValue, Iterable<LzrValue> {

    companion object {

        @JvmStatic
        fun of(vararg values: LzrValue): LzrArray {
            return LzrArray(arrayOf(*values))
        }

        @JvmStatic
        fun of(array: ByteArray): LzrArray {
            return LzrArray(array.size) {
                LzrNumber.of(array[it].toInt())
            }
        }

        @JvmStatic
        fun of(array: Array<String>): LzrArray {
            return LzrArray(array.size) {
                LzrString(array[it])
            }
        }

        @JvmStatic
        fun add(array: LzrArray, value: LzrValue): LzrArray {
            val last = array.elements.size
            val result = LzrArray(last + 1)
            array.elements.copyInto(result.elements)
            result.elements[last] = value
            return result
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
                if (sb.isNotEmpty()) sb.append(delimiter)
                else sb.append(prefix)
                sb.append(value.asString())
            }
            sb.append(suffix)
            return LzrString(sb.toString())
        }
    }

    private val elements: Array<LzrValue>

    constructor(size: Int) {
        val defaultNull = LzrNull()
        this.elements = Array(size) { defaultNull }
    }

    constructor(size: Int, init: (index: Int) -> LzrValue) {
        this.elements = Array(size, init)
    }

    constructor(values: Array<LzrValue>) {
        this.elements = values.copyOf()
    }

    constructor(values: List<LzrValue>) {
        this.elements = values.toTypedArray()
    }

    constructor(array: LzrArray) : this(array.elements)

    open val copyElements: Array<LzrValue>
        get() {
            return elements.copyOf()
        }

    override fun type(): Int {
        return Types.ARRAY
    }

    open fun size(): Int {
        return elements.size
    }

    open operator fun get(index: Int): LzrValue {
        return elements[index]
    }

    operator fun get(index: LzrValue): LzrValue {
        return when (index.asString()) {
            "length" -> LzrNumber.of(size())
            "isEmpty" -> Converters.voidToBoolean { size() == 0 }
            else -> get(index.asInt())
        }
    }

    open operator fun set(index: Int, value: LzrValue) {
        elements[index] = value
    }

    override fun raw(): Array<LzrValue> {
        return elements
    }

    override fun asInt(): Int {
        throwTypeCastException("array", "integer")
    }

    override fun asNumber(): Double {
        throwTypeCastException("array", "number")
    }

    override fun asString(): String {
        return elements.contentToString()
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun iterator(): Iterator<LzrValue> {
        return elements.iterator()
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 79 * hash + elements.contentDeepHashCode()
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        other as LzrArray
        return elements.contentDeepEquals(other.elements)
    }

    override fun compareTo(other: LzrValue): Int {
        other.asLzrArrayOrNull()?.also {
            val lengthCompare = size().compareTo(it.size())
            if (lengthCompare != 0) return lengthCompare
        }
        return asString().compareTo(other.asString())
    }

    override fun toString(): String {
        return asString()
    }

}
