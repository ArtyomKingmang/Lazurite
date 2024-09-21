package com.kingmang.lazurite.runtime.values

import com.kingmang.lazurite.core.*
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Keyword
import java.util.*

class LzrString(private val value: String) : LzrValue {
    fun access(propertyValue: LzrValue): LzrValue {
        val prop = propertyValue.asString()
        val knownProps = when (prop) {
            "length" -> Function { args ->
                LzrNumber.of(this.length())
            }
            "isEmpty" -> Function { args ->
                LzrNumber.fromBoolean(this.isEmpty())
            }

            "lower" -> Function { args ->
                args.check(0)
                LzrString(this.lower())
            }
            "upper" -> Function { args ->
                args.check(0)
                LzrString(this.upper())
            }
            "isLower" -> Function { args ->
                args.check(0)
                LzrNumber.fromBoolean(this.isLower())
            }
            "repeat" -> Function { args ->
                args.check(1)
                val buffer = StringBuilder()
                for(i in 1..args[0].asInt()){
                    buffer.append(value);
                }
                LzrString(buffer.toString());
            }
            "isUpper" -> Function { args ->
                args.check(0)
                LzrNumber.fromBoolean(this.isUpper())
            }

            "chars" -> Function { args ->
                args.check(0)
                LzrArray(length()) { LzrNumber.of(value[it].code) }
            }
            "trim" -> Function { args ->
                args.check(0)
                LzrString(this.trim())
            }
            "startsWith" -> Function { args ->
                args.checkOrOr(1, 2)
                val value = when (args.size) {
                    2 -> {
                        this.startsWith(args[0].asString(), args[0].asInt() != 0) // 0 - false, other - true
                    }
                    else -> {
                        this.startsWith(args[0].asString())
                    }
                }
                LzrNumber.fromBoolean(value)
            }
            "endsWith" -> Function { args ->
                args.checkOrOr(1, 2)
                val value = when (args.size) {
                    2 -> {
                        this.endsWith(args[0].asString(), args[0].asInt() != 0) // 0 - false, other - true
                    }
                    else -> {
                        this.endsWith(args[0].asString())
                    }
                }
                LzrNumber.fromBoolean(value)
            }
            "matches" -> Function { args ->
                args.check(1)
                LzrNumber.fromBoolean(this.matches(args[0].asString()))
            }
            "equals" -> Function { args ->
                args.check(1)
                LzrNumber.fromBoolean(this.value == args[0].asString())
            }
            else -> null
        }
        if (knownProps != null)
            return LzrFunction(knownProps)
        if (Keyword.isExists(prop))
            return LzrFunction { args -> Keyword.get(prop).execute(*arrayOf<LzrValue>(this).plus(args)) }
        throw LzrException("UnknownPropertyException", prop)
    }

    fun length(): Int =
        this.value.length

    fun isEmpty(): Boolean =
        this.length() == 0

    fun lower(locale: Locale = Locale.getDefault()): String =
        this.value.lowercase()

    fun upper(locale: Locale = Locale.getDefault()): String =
        this.value.uppercase()

    fun isLower(locale: Locale = Locale.getDefault()): Boolean =
        this.value == this.lower(locale)

    fun isUpper(locale: Locale = Locale.getDefault()): Boolean =
        this.value == this.upper(locale)

    fun trim(): String =
        this.value.trim()

    fun startsWith(prefix: LzrString, ignoreCase: Boolean = false): Boolean =
        this.endsWith(prefix.asString(), ignoreCase)

    fun startsWith(prefix: String, ignoreCase: Boolean = false): Boolean =
        this.value.startsWith(prefix, ignoreCase)

    fun endsWith(postfix: LzrString, ignoreCase: Boolean = false): Boolean =
        this.endsWith(postfix.asString(), ignoreCase)

    fun endsWith(postfix: String, ignoreCase: Boolean = false): Boolean =
        this.value.endsWith(postfix, ignoreCase)

    fun matches(regex: LzrString): Boolean =
        this.matches(regex.asString())

    fun matches(regex: String): Boolean =
        this.matches(regex.toRegex())

    fun matches(regex: Regex): Boolean =
        this.value.matches(regex)

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
