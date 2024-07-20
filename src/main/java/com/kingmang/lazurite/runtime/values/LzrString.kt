package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Converters
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrStringOrNull
import com.kingmang.lazurite.core.checkOrOr
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Keyword
import java.util.*

class LzrString(private val value: String) : LzrValue {
    fun access(propertyValue: LzrValue): LzrValue {
        val prop = propertyValue.asString()
        val knownProps = when (prop) {
            "length" -> LzrNumber.of(length())
            "toLowerCase" -> LzrString(value.lowercase(Locale.getDefault()))
            "toUpperCase" -> LzrString(value.uppercase(Locale.getDefault()))
            "chars" -> LzrArray(length()) { LzrNumber.of(value[it].code) }
            "trim" -> Converters.voidToString { value.trim() }
            "startsWith" -> LzrFunction { args ->
                args.checkOrOr(1, 2)
                val offset = if ((args.size == 2)) args[1].asInt() else 0
                LzrNumber.fromBoolean(this.value.startsWith(args[0].asString(), offset))
            }
            "endsWith" -> Converters.stringToBoolean { suffix: String -> this.value.endsWith(suffix) }
            "matches" -> Converters.stringToBoolean { regex: String -> this.value.matches(regex.toRegex()) }
            "equals" -> Converters.stringToBoolean { anotherString: String -> this.value.equals(anotherString, ignoreCase = true) }
            "isEmpty" -> Converters.voidToBoolean { this.value.isEmpty() }
            else -> null
        }
        if (knownProps != null)
            return knownProps
        if (Keyword.isExists(prop))
            return LzrFunction { args -> Keyword.get(prop).execute(*arrayOf<LzrValue>(this).plus(args)) }
        throw LzrException("UnknownPropertyException", prop)
    }

    fun length(): Int =
        this.value.length

    override fun type(): Int =
        Types.STRING

    override fun raw(): String =
        this.value

    override fun asInt(): Int =
        this.value.toInt()

    override fun asNumber(): Double =
        this.value.toDouble()

    override fun asString(): String =
        this.value

    override fun asArray(): IntArray =
        IntArray(0)

    override fun hashCode(): Int {
        var hash = 3
        hash = 97 * hash + Objects.hashCode(this.value)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || this.javaClass != other.javaClass)
            return false
        other as LzrString
        return this.value == other.value
    }

    override fun compareTo(other: LzrValue): Int =
        other.asLzrStringOrNull()
            ?.let { this.value.compareTo(it.value) }
            ?: this.asString().compareTo(other.asString())

    override fun toString(): String =
        this.asString()

    companion object {
        @JvmField
        val EMPTY = LzrString("")
    }
}
