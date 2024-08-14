package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.check
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.*
import java.util.*

class ClassInstanceValue(
    private val className: String,
    private val attrs: LzrMap
) : LzrValue {

    init {
        this["__name__"] = Function { args ->
            args.check(0)
            LzrString(this.className)
        }
    }

    fun access(attr: LzrValue): LzrValue =
        this[attr]

    operator fun get(attr: LzrValue): LzrValue {
        val hasGetMethod: Boolean = this.has("__get__")

        if (attr.type() == Types.STRING) {
            if (attr.asString() == "__get__" && hasGetMethod) {
                return this.baseGet("__get__")
            }
        }

        if (hasGetMethod) {
            return this.callMethod("__get__", attr)
        }

        return this.baseGet(attr)
    }

    operator fun get(attr: String): LzrValue =
        this[LzrString(attr)]

    operator fun get(attr: Number): LzrValue =
        this[LzrNumber(attr)]

    fun getOrNull(attr: LzrValue): LzrValue? =
        if (this.has("__getOrNull__")) {
            val value = this.callMethod("__getOrNull__", attr)
            if (value == LzrNull) {
                null
            } else {
                value
            }
        } else {
            this.baseGetOrNull(attr)
        }

    fun getOrNull(attr: String): LzrValue? =
        this.getOrNull(LzrString(attr))

    fun getOrNull(attr: Number): LzrValue? =
        this.getOrNull(LzrNumber(attr))

    operator fun set(attr: LzrValue, value: LzrValue) =
        if (this.has("__set__")) {
            this.callMethod("__set__", attr, value)
            Unit
        } else {
            this.baseSet(attr, value)
        }

    operator fun set(attr: LzrValue, value: Function) {
        this[attr] = LzrFunction(value)
    }

    operator fun set(attr: LzrValue, value: String) {
        this[attr] = LzrString(value)
    }

    operator fun set(attr: LzrValue, value: Number) {
        this[attr] = LzrNumber(value)
    }

    operator fun set(attr: String, value: LzrValue) {
        this[LzrString(attr)] = value
    }

    operator fun set(attr: String, value: Function) {
        this[LzrString(attr)] = value
    }

    operator fun set(attr: String, value: String) {
        this[LzrString(attr)] = LzrString(value)
    }

    operator fun set(attr: String, value: Number) {
        this[LzrString(attr)] = LzrNumber(value)
    }

    // has

    fun has(attr: LzrValue): Boolean =
        this.attrs[attr] != null

    fun has(attr: String): Boolean =
        this.has(LzrString(attr))

    fun has(attr: Number): Boolean =
        this.has(LzrNumber(attr))

    // Базовые get, set
    // Игнорируют пользовательские методы __get__, __set__
    // Не используйте их
    // Исключение: Встроенные функции getAttr, setAttr

    fun baseGet(attr: LzrValue): LzrValue =
        this.attrs[attr]
            ?: throw LzrException("AttributeException", "instance of class '$className' does not have attribute '${attr.asString()}'")

    fun baseGet(attr: String): LzrValue =
        this.baseGet(LzrString(attr))

    fun baseGet(attr: Number): LzrValue =
        this.baseGet(LzrNumber(attr))

    fun baseGetOrNull(attr: LzrValue): LzrValue? =
        this.attrs[attr]

    fun baseGetOrNull(attr: String): LzrValue? =
        this.baseGetOrNull(LzrString(attr))

    fun baseGetOrNull(attr: Number): LzrValue? =
        this.baseGetOrNull(LzrNumber(attr))

    // baseSet(LzrValue, ...)

    fun baseSet(attr: LzrValue, value: LzrValue) {
        this.attrs[attr] = value
    }

    fun baseSet(attr: LzrValue, value: Function) =
        this.baseSet(attr, LzrFunction(value))

    fun baseSet(attr: LzrValue, value: String) =
        this.baseSet(attr, LzrString(value))

    fun baseSet(attr: LzrValue, value: Number) =
        this.baseSet(attr, LzrNumber(value))

    // baseSet(String, ...)

    fun baseSet(attr: String, value: LzrValue) =
        this.baseSet(LzrString(attr), value)

    fun baseSet(attr: String, value: Function) =
        this.baseSet(LzrString(attr), LzrFunction(value))

    fun baseSet(attr: String, value: String) =
        this.baseSet(LzrString(attr), LzrString(attr))

    fun baseSet(attr: String, value: Number) =
        this.baseSet(LzrString(attr), LzrNumber(value))

    // callMethod

    fun callMethod(attr: LzrValue, vararg args: LzrValue): LzrValue {
        val method = this[attr]
        return if (method is LzrFunction) {
            method.value.execute(*args)
        } else {
            throw LzrException("AttributeException", "In instance of class '$className', '$attr' is not a method")
        }
    }

    fun callMethod(attr: String, vararg args: LzrValue): LzrValue =
        this.callMethod(LzrString(attr), *args)

    fun callMethodOrNull(attr: LzrValue, vararg args: LzrValue): LzrValue? {
        val method = this.getOrNull(attr)
        return if (method is LzrFunction) {
            method.value.execute(*args)
        } else {
            null
        }
    }

    fun callMethodOrNull(attr: String, vararg args: LzrValue): LzrValue? =
        this.callMethodOrNull(LzrString(attr), *args)

    // override functions

    override fun raw(): Any? =
        null

    override fun asInt(): Int {
        return this.callMethod("__int__").asInt()
    }

    override fun asNumber(): Double {
        return this.callMethod("__number__").asNumber()
    }

    override fun asString(): String {
        return this.callMethodOrNull("__string__")?.asString()
            ?: this.baseAsString()
    }

    fun baseAsString(): String =
        "<instance of class '$className' at ${this.hashCode()}>"

    override fun asArray(): IntArray =
        IntArray(0)

    override fun type(): Int =
        Types.CLASS

    override fun hashCode(): Int {
        var hash = 5
        hash = 37 * hash + Objects.hash(this.className, this.attrs)
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this.javaClass != other.javaClass)
            return false
        other as ClassInstanceValue
        return this.className == other.className && (this.attrs == other.attrs)
    }

    override fun compareTo(other: LzrValue): Int =
        this.asString().compareTo(other.asString())

    override fun toString(): String =
        this.asString()
}