package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.Converters
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrStringOrNull
import com.kingmang.lazurite.core.checkOrOr
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Keyword
import java.util.*

class LzrString(private val value: String) : LzrValue {

    companion object {
        @JvmField
        val EMPTY = LzrString("")
    }

    fun access(propertyValue: LzrValue): LzrValue {
        val prop = propertyValue.asString()
        val knownProps = when (prop) {
            "length" -> LzrNumber.of(length())
            "toLowerCase" -> LzrString(value.lowercase(Locale.getDefault()))
            "toUpperCase" -> LzrString(value.uppercase(Locale.getDefault()))
            "chars" -> LzrArray(length()) {
                LzrNumber.of(value[it].code)
            }

            "trim" -> Converters.voidToString {
                value.trim()
            }

            "startsWith" -> LzrFunction { args ->
                args.checkOrOr(1, 2)
                val offset = if ((args.size == 2)) args[1].asInt() else 0
                LzrNumber.fromBoolean(value.startsWith(args[0].asString(), offset))
            }

            "endsWith" -> Converters.stringToBoolean { suffix: String ->
                value.endsWith(suffix)
            }

            "matches" -> Converters.stringToBoolean { regex: String ->
                value.matches(regex.toRegex())
            }

            "equals" -> Converters.stringToBoolean { anotherString: String ->
                value.equals(anotherString, ignoreCase = true)
            }

            "isEmpty" -> Converters.voidToBoolean { value.isEmpty() }
            else -> null
        }
        if (knownProps != null) return knownProps
        if (Keyword.isExists(prop)) {
            val function = Keyword.get(prop)
            return LzrFunction { args ->
                val newArgs = arrayOf<LzrValue>(this).plus(args)
                function.execute(*newArgs)
            }
        }
        throw LzrException("UnknownPropertyException", prop)
    }

    fun length(): Int {
        return value.length
    }

    override fun type(): Int {
        return Types.STRING
    }

    override fun raw(): String {
        return value
    }

    override fun asInt(): Int {
        return value.toInt()
    }

    override fun asNumber(): Double {
        return value.toDouble()
    }

    override fun asString(): String {
        return value
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun hashCode(): Int {
        var hash = 3
        hash = 97 * hash + Objects.hashCode(this.value)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        other as LzrString
        return this.value == other.value
    }

    override fun compareTo(other: LzrValue): Int {
        return other.asLzrStringOrNull()
            ?.let { value.compareTo(it.value) }
            ?: asString().compareTo(other.asString())
    }

    override fun toString(): String {
        return asString()
    }
}
