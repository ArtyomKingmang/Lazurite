package com.kingmang.lazurite.utils

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Modifier

object ValueUtils {

    @JvmStatic
    @Throws(JSONException::class)
    fun toObject(value: LzrValue): Any = when (value.type()) {
        Types.ARRAY -> (value as LzrArray).toJsonArray()
        Types.MAP -> (value as LzrMap).toJsonObject()
        Types.NUMBER -> value.raw()
        Types.STRING -> value.asString()
        else -> JSONObject.NULL
    }

    @JvmStatic
    @Throws(JSONException::class)
    fun toValue(obj: Any): LzrValue = when (obj) {
        is JSONObject -> obj.toLzrMap()
        is JSONArray -> obj.toLzrArray()
        is String -> LzrString(obj)
        is Number -> LzrNumber.of(obj)
        is Boolean -> LzrNumber.fromBoolean(obj)
        else -> LzrNumber.ZERO
    }

    @JvmStatic
    fun getNumber(value: LzrValue): Number {
        if (value.type() == Types.NUMBER) return (value as LzrNumber).raw()
        return value.asInt()
    }

    @JvmStatic
    fun getFloatNumber(value: LzrValue): Float {
        if (value.type() == Types.NUMBER) return (value as LzrNumber).raw().toFloat()
        return value.asNumber().toFloat()
    }

    @JvmStatic
    fun toByteArray(array: LzrArray): ByteArray {
        return ByteArray(array.size()) {
            array[it].asInt().toByte()
        }
    }

    @JvmStatic
    fun consumeFunction(value: LzrValue, argumentNumber: Int): Function {
        return consumeFunction(value, " at argument ${argumentNumber + 1}")
    }

    fun <T : Number> collectNumberConstants(clazz: Class<*>, type: Class<T>): LzrMap {
        val result = LzrMap(20)
        for (field in clazz.declaredFields) {
            if (!Modifier.isStatic(field.modifiers)) continue
            if (field.type != type) continue
            try {
                result[field.name] = LzrNumber.of(field[type] as T)
            } catch (ignore: IllegalAccessException) {
            }
        }
        return result
    }

    private fun LzrMap.toJsonObject(): JSONObject {
        val result = JSONObject(LinkedHashMap<String, Any>())
        forEach { entry ->
            result.put(entry.key.asString(), toObject(entry.value))
        }
        return result
    }

    private fun LzrArray.toJsonArray(): JSONArray {
        val result = JSONArray()
        forEach { value ->
            result.put(toObject(value))
        }
        return result
    }

    private fun JSONObject.toLzrMap(): LzrMap {
        val result = LzrMap(length())
        keys().forEach { key ->
            result[LzrString(key)] = toValue(get(key))
        }
        return result
    }

    private fun JSONArray.toLzrArray(): LzrArray {
        val length = length()
        val result = LzrArray(length)
        for (i in 0 until length) {
            result[i] = toValue(get(i))
        }
        return result
    }

    private fun consumeFunction(value: LzrValue, errorMessage: String): Function {
        if (value.type() != Types.FUNCTION) {
            throw LzrException(
                "TypeException",
                "Function expected$errorMessage, but found ${Types.typeToString(value.type())}"
            )
        }
        return (value as LzrFunction).value
    }
}