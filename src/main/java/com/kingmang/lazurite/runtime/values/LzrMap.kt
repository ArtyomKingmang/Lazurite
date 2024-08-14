package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrMapOrNull
import com.kingmang.lazurite.core.throwTypeCastException
import java.util.*
import java.util.function.Consumer

// TODO remove JvmSuppressWildcards annotations after completely move to kotlin
open class LzrMap : LzrValue, Iterable<Map.Entry<@JvmSuppressWildcards LzrValue, @JvmSuppressWildcards LzrValue>> {
    private val map: MutableMap<LzrValue, LzrValue>

    constructor(size: Int) {
        this.map = LinkedHashMap(size)
    }

    constructor(map: Map<LzrValue, LzrValue>) {
        this.map = map.toMutableMap()
    }

    fun ifPresent(key: String, consumer: Consumer<LzrValue>): Boolean =
        ifPresent(LzrString(key), consumer)

    fun ifPresent(key: LzrValue, consumer: Consumer<LzrValue>): Boolean {
        if (map.containsKey(key)) {
            consumer.accept(map.getValue(key))
            return true
        }
        return false
    }

    fun toPairs(): LzrArray =
        LzrArray(this.map.map { LzrArray.of(it.key, it.value) })

    fun getMap(): Map<LzrValue, LzrValue> =
        this.map

    override fun type(): Int =
        Types.MAP

    fun size(): Int =
        this.map.size

    fun isEmpty(): Boolean =
        this.size() == 0

    open fun containsKey(key: LzrValue): Boolean =
        this.map.containsKey(key)

    open fun merge(map: LzrMap): LzrMap =
        LzrMap.merge(this, map)

    open operator fun get(key: LzrValue): LzrValue? =
        this.map[key]

    open operator fun get(key: String): LzrValue? =
        this[LzrString(key)]

    operator fun set(key: String, value: LzrValue) {
        set(LzrString(key), value)
    }

    operator fun set(key: String, function: Function) {
        set(LzrString(key), LzrFunction(function))
    }

    operator fun set(key: LzrValue, value: LzrValue) {
        map[key] = value
    }

    override fun raw(): Any? =
        this.map

    override fun asInt(): Int =
        throwTypeCastException("map", "integer")

    override fun asNumber(): Double =
        throwTypeCastException("map", "number")

    override fun asString(): String =
        this.map.toString()

    override fun asArray(): IntArray =
        IntArray(0)

    override fun iterator(): Iterator<Map.Entry<LzrValue, LzrValue>> =
        this.map.entries.iterator()

    override fun hashCode(): Int {
        var hash = 5
        hash = 37 * hash + Objects.hashCode(this.map)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || this.javaClass != other.javaClass)
            return false
        other as LzrMap
        return this.map == other.map
    }

    override fun compareTo(other: LzrValue): Int {
        other.asLzrMapOrNull()?.let {
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
        @JvmField
        val EMPTY = LzrMap(1)

        @JvmStatic
        fun merge(map1: LzrMap, map2: LzrMap): LzrMap =
            LzrMap(map1.size() + map2.size()).apply {
                map.putAll(map1.map)
                map.putAll(map2.map)
            }
    }
}
