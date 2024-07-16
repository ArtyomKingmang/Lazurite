package com.kingmang.lazurite.runtime.values

class LzrEnum(private val enums: Map<String, LzrString>) : LzrValue {

    fun get(enm: String): LzrString? {
        return enums[enm]
    }

    override fun raw(): Map<String, LzrString> {
        return enums
    }

    override fun asInt(): Int {
        return enums.size
    }

    override fun asNumber(): Double {
        return enums.size.toDouble()
    }

    override fun asString(): String {
        return enums.toString()
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun type(): Int {
        return 0
    }

    override fun compareTo(other: LzrValue): Int {
        return 0
    }
}
