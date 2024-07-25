package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.exceptions.LzrException
import java.io.Serializable

class LzrReference(
    @JvmField val ref: Any
) : LzrValue, Serializable {


    fun getRef() : Any?{
        return ref
    }

    override fun raw(): Any? {
        return ref
    }

    override fun asInt(): Int {
        throw LzrException("BadArithmetic", "Cannot cast reference to a Integer")
    }

    override fun asNumber(): Double {
        throw LzrException("BadArithmetic", "Cannot cast reference to a Number")
    }

    override fun asString(): String {
        return "LzrReference " + hashCode()
    }

    override fun asArray(): IntArray {
        throw LzrException("BadArithmetic", "Cannot cast reference to a Array")
    }

    override fun type(): Int {
        return 0
    }

    override fun compareTo(o: LzrValue): Int {
        return 0
    }
}
