package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrValue
import java.io.Serializable

class Reference(private val ref: Any) : LzrValue, Serializable {
    override fun raw(): Any =
        ref

    override fun asInt(): Int =
        throw LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER")

    override fun asNumber(): Double =
        throw LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER")

    override fun asString(): String =
        "#Reference<${hashCode()}>"

    override fun asArray(): IntArray = // todo: Почему в случае с asInt | asNumber мы кидаем исключение, а тут просто создаём пустой массив интов?
        intArrayOf()

    override fun type(): Int =
        0

    override fun compareTo(other: LzrValue): Int = // todo: Меня берут большие сомнения...
        0
}