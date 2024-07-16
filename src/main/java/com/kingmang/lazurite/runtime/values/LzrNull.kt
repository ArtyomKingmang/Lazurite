package com.kingmang.lazurite.runtime.values

class LzrNull : LzrValue {

    override fun raw(): Any? {
        return null
    }

    override fun asInt(): Int {
        return 0
    }

    override fun asNumber(): Double {
        return 0.0
    }

    override fun asString(): String {
        return "null"
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun type(): Int {
        return 482862660
    }

    override fun compareTo(other: LzrValue): Int {
        if (other.raw() == null) return 0
        return -1
    }

    override fun toString(): String {
        return asString()
    }
}