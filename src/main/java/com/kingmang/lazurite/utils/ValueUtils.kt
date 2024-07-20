package com.kingmang.lazurite.utils

import com.kingmang.lazurite.core.*
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.runtime.values.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Modifier

object ValueUtils {
    @JvmStatic
    @Throws(JSONException::class)
    fun toObject(value: LzrValue): Any =
        when (value.type()) {
            Types.ARRAY -> value.asLzrArray().toJsonArray()
            Types.MAP -> value.asLzrMap().toJsonObject()
            Types.NUMBER -> value.asLzrNumber().raw()
            Types.STRING -> value.asLzrString().raw()
            else -> JSONObject.NULL
        }

    @JvmStatic
    @Throws(JSONException::class)
    fun toValue(obj: Any): LzrValue =
        when (obj) {
            is JSONObject -> obj.toLzrMap()
            is JSONArray -> obj.toLzrArray()
            is String -> LzrString(obj)
            is Number -> LzrNumber.of(obj)
            is Boolean -> LzrNumber.fromBoolean(obj)
            else -> LzrNumber.ZERO
        }

    @JvmStatic
    fun getNumber(value: LzrValue): Number =
        value.asLzrNumberOrNull()?.raw() ?: value.asInt()

    @JvmStatic
    fun getFloatNumber(value: LzrValue): Float =
        value.asLzrNumberOrNull()?.raw()?.toFloat() ?: value.asNumber().toFloat()

    @JvmStatic
    fun toByteArray(array: LzrArray): ByteArray =
        ByteArray(array.size()) { array[it].asInt().toByte() }

    @JvmStatic
    fun consumeFunction(value: LzrValue, argumentNumber: Int): Function =
        value.asLzrFunction { "Function expected at argument ${argumentNumber + 1}, but found ${Types.typeToString(value.type())}" }.value

    @Suppress("UNCHECKED_CAST")
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
        forEach { (key, value) -> result.put(key.asString(), toObject(value)) }
        return result
    }

    private fun LzrArray.toJsonArray(): JSONArray {
        val result = JSONArray()
        asSequence().map(::toObject).forEach(result::put)
        return result
    }

    private fun JSONObject.toLzrMap(): LzrMap {
        val result = LzrMap(length())
        keys().forEach { result[LzrString(it)] = toValue(get(it)) }
        return result
    }

    private fun JSONArray.toLzrArray(): LzrArray =
        LzrArray(length()) { toValue(get(it)) }
}