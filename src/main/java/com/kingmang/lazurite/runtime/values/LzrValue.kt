package com.kingmang.lazurite.runtime.values

interface LzrValue : Comparable<LzrValue> {
    fun raw(): Any?

    fun asInt(): Int

    fun asNumber(): Double

    fun asString(): String

    fun asArray(): IntArray

    fun type(): Int
}
