package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrMapOrNull
import com.kingmang.lazurite.core.throwTypeCastException
import java.util.*
import java.util.function.Consumer

// TODO remove JvmSuppressWildcards annotations after completely move to kotlin
open class LzrMap : LzrValue, Iterable<Map.Entry<@JvmSuppressWildcards LzrValue, @JvmSuppressWildcards LzrValue>> {

    companion object {
        @JvmField
        val EMPTY = LzrMap(1)

        @JvmStatic
        fun merge(map1: LzrMap, map2: LzrMap): LzrMap {
            val result = LzrMap(map1.size() + map2.size())
            result.map.putAll(map1.map)
            result.map.putAll(map2.map)
            return result
        }
    }

    private val map: MutableMap<LzrValue, LzrValue>

    constructor(size: Int) {
        this.map = LinkedHashMap(size)
    }

    constructor(map: Map<LzrValue, LzrValue>) {
        this.map = map.toMutableMap()
    }

    fun ifPresent(key: String, consumer: Consumer<LzrValue>): Boolean {
        return ifPresent(LzrString(key), consumer)
    }

    fun ifPresent(key: LzrValue, consumer: Consumer<LzrValue>): Boolean {
        if (map.containsKey(key)) {
            consumer.accept(map.getValue(key))
            return true
        }
        return false
    }

    fun toPairs(): LzrArray {
        return LzrArray(map.map { LzrArray.of(it.key, it.value) })
    }

    fun getMap(): Map<LzrValue, LzrValue> {
        return map
    }

    override fun type(): Int {
        return Types.MAP
    }

    fun size(): Int {
        return map.size
    }

    open fun containsKey(key: LzrValue): Boolean {
        return map.containsKey(key)
    }

    open operator fun get(key: LzrValue): LzrValue? {
        return map[key]
    }

    operator fun set(key: String, value: LzrValue) {
        set(LzrString(key), value)
    }

    operator fun set(key: String, function: Function) {
        set(LzrString(key), LzrFunction(function))
    }

    operator fun set(key: LzrValue, value: LzrValue) {
        map[key] = value
    }

    override fun raw(): Any? {
        return map
    }

    override fun asInt(): Int {
        throwTypeCastException("map", "integer")
    }

    override fun asNumber(): Double {
        throwTypeCastException("map", "number")
    }

    override fun asString(): String {
        return map.toString()
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun iterator(): Iterator<Map.Entry<LzrValue, LzrValue>> {
        return map.entries.iterator()
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 37 * hash + Objects.hashCode(this.map)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        other as LzrMap
        return this.map == other.map
    }

    override fun compareTo(other: LzrValue): Int {
        other.asLzrMapOrNull()?.also {
            val lengthCompare = size().compareTo(it.size())
            if (lengthCompare != 0) return lengthCompare
        }
        return asString().compareTo(other.asString())
    }

    override fun toString(): String {
        return asString()
    }
}
