package com.kingmang.lazurite.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.Types.*;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@NoArgsConstructor
public final class ValueUtils {

    public static Object toObject(LzrValue val) throws JSONException {
        switch (val.type()) {
            case Types.ARRAY:
                return toObject((LzrArray) val);
            case Types.MAP:
                return toObject((LzrMap) val);
            case Types.NUMBER:
                return val.raw();
            case Types.STRING:
                return val.asString();
            default:
                return JSONObject.NULL;
        }
    }

    public static JSONObject toObject(LzrMap map) throws JSONException {
        final JSONObject result = new JSONObject(new LinkedHashMap<String, Object>());
        for (Map.Entry<LzrValue, LzrValue> entry : map) {
            final String key = entry.getKey().asString();
            final Object value = toObject(entry.getValue());
            result.put(key, value);
        }
        return result;
    }

    public static JSONArray toObject(LzrArray array) throws JSONException {
        final JSONArray result = new JSONArray();
        for (LzrValue value : array) {
            result.put(toObject(value));
        }
        return result;
    }

    public static LzrValue toValue(Object obj) throws JSONException {
        if (obj instanceof JSONObject) {
            return toValue((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            return toValue((JSONArray) obj);
        }
        if (obj instanceof String) {
            return new LzrString((String) obj);
        }
        if (obj instanceof Number) {
            return LzrNumber.of(((Number) obj));
        }
        if (obj instanceof Boolean) {
            return LzrNumber.fromBoolean((Boolean) obj);
        }
        // NULL or other
        return LzrNumber.ZERO;
    }

    public static LzrMap toValue(JSONObject json) throws JSONException {
        final LzrMap result = new LzrMap(new LinkedHashMap<>(json.length()));
        final Iterator<String> it = json.keys();
        while(it.hasNext()) {
            final String key = it.next();
            final LzrValue value = toValue(json.get(key));
            result.set(new LzrString(key), value);
        }
        return result;
    }

    public static LzrArray toValue(JSONArray json) throws JSONException {
        final int length = json.length();
        final LzrArray result = new LzrArray(length);
        for (int i = 0; i < length; i++) {
            final LzrValue value = toValue(json.get(i));
            result.set(i, value);
        }
        return result;
    }

    public static Number getNumber(LzrValue value) {
        if (value.type() == Types.NUMBER) return ((LzrNumber) value).raw();
        return value.asInt();
    }

    public static float getFloatNumber(LzrValue value) {
        if (value.type() == Types.NUMBER) return ((LzrNumber) value).raw().floatValue();
        return (float) value.asNumber();
    }

    public static byte[] toByteArray(LzrArray array) {
        final int size = array.size();
        final byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) array.get(i).asInt();
        }
        return result;
    }

    public static Function consumeFunction(LzrValue value, int argumentNumber) {
        return consumeFunction(value, " at argument " + (argumentNumber + 1));
    }

    public static Function consumeFunction(LzrValue value, String errorMessage) {
        final int type = value.type();
        if (type != Types.FUNCTION) {
            throw new LZRException("TypeExeption ", "Function expected" + errorMessage
                    + ", but found " + Types.typeToString(type));
        }
        return ((LzrFunction) value).getValue();
    }

    public static <T extends Number> LzrMap collectNumberConstants(Class<?> clazz, Class<T> type) {
        LzrMap result = new LzrMap(20);
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!field.getType().equals(type)) continue;
            try {
                result.set(field.getName(), LzrNumber.of((T) field.get(type)));
            } catch (IllegalAccessException ignore) {
            }
        }
        return result;
    }
}