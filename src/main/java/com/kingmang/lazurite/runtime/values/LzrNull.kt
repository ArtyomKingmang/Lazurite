package com.kingmang.lazurite.runtime.values

object LzrNull : LzrValue {
    override fun raw(): Any? =
        null

    override fun asInt(): Int =
        0

    override fun asNumber(): Double =
        0.0

    override fun asString(): String =
        "null"

    override fun asArray(): IntArray =
        IntArray(0)

    override fun type(): Int =
        482862660

    override fun compareTo(other: LzrValue): Int {
        other.raw() ?: return 0
        return -1
    }

    override fun toString(): String =
        this.asString()
}