package com.kingmang.lazurite.runtime.values

class LzrEnum(private val enums: Map<String, LzrString>) : LzrValue {
    fun get(enm: String): LzrString? =
        this.enums[enm]

    override fun raw(): Map<String, LzrString> =
        this.enums

    override fun asInt(): Int =
        this.enums.size

    override fun asNumber(): Double =
        this.enums.size.toDouble()

    override fun asString(): String =
        this.enums.toString()

    override fun asArray(): IntArray =
        IntArray(0)

    override fun type(): Int =
        0

    override fun compareTo(other: LzrValue): Int =
        0
}
