package com.kingmang.lazurite.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kingmang.lazurite.LZREx.LzrExeption;
import com.kingmang.lazurite.runtime.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ValueUtils {

    private ValueUtils() { }

    public static Object toObject(Value val) throws JSONException {
        switch (val.type()) {
            case Types.ARRAY:
                return toObject((ArrayValue) val);
            case Types.MAP:
                return toObject((MapValue) val);
            case Types.NUMBER:
                return val.raw();
            case Types.STRING:
                return val.asString();
            default:
                return JSONObject.NULL;
        }
    }

    public static JSONObject toObject(MapValue map) throws JSONException {
        final JSONObject result = new JSONObject(new LinkedHashMap<String, Object>());
        for (Map.Entry<Value, Value> entry : map) {
            final String key = entry.getKey().asString();
            final Object value = toObject(entry.getValue());
            result.put(key, value);
        }
        return result;
    }

    public static JSONArray toObject(ArrayValue array) throws JSONException {
        final JSONArray result = new JSONArray();
        for (Value value : array) {
            result.put(toObject(value));
        }
        return result;
    }

    public static Value toValue(Object obj) throws JSONException {
        if (obj instanceof JSONObject) {
            return toValue((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            return toValue((JSONArray) obj);
        }
        if (obj instanceof String) {
            return new StringValue((String) obj);
        }
        if (obj instanceof Number) {
            return NumberValue.of(((Number) obj));
        }
        if (obj instanceof Boolean) {
            return NumberValue.fromBoolean((Boolean) obj);
        }
        // NULL or other
        return NumberValue.ZERO;
    }

    public static MapValue toValue(JSONObject json) throws JSONException {
        final MapValue result = new MapValue(new LinkedHashMap<>(json.length()));
        final Iterator<String> it = json.keys();
        while(it.hasNext()) {
            final String key = it.next();
            final Value value = toValue(json.get(key));
            result.set(new StringValue(key), value);
        }
        return result;
    }

    public static ArrayValue toValue(JSONArray json) throws JSONException {
        final int length = json.length();
        final ArrayValue result = new ArrayValue(length);
        for (int i = 0; i < length; i++) {
            final Value value = toValue(json.get(i));
            result.set(i, value);
        }
        return result;
    }

    public static Number getNumber(Value value) {
        if (value.type() == Types.NUMBER) return ((NumberValue) value).raw();
        return value.asInt();
    }

    public static float getFloatNumber(Value value) {
        if (value.type() == Types.NUMBER) return ((NumberValue) value).raw().floatValue();
        return (float) value.asNumber();
    }

    public static byte[] toByteArray(ArrayValue array) {
        final int size = array.size();
        final byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) array.get(i).asInt();
        }
        return result;
    }

    public static Function consumeFunction(Value value, int argumentNumber) {
        return consumeFunction(value, " at argument " + (argumentNumber + 1));
    }

    public static Function consumeFunction(Value value, String errorMessage) {
        final int type = value.type();
        if (type != Types.FUNCTION) {
            throw new LzrExeption("TypeExeption ", "Function expected" + errorMessage
                    + ", but found " + Types.typeToString(type));
        }
        return ((FunctionValue) value).getValue();
    }

    public static <T extends Number> MapValue collectNumberConstants(Class<?> clazz, Class<T> type) {
        MapValue result = new MapValue(20);
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!field.getType().equals(type)) continue;
            try {
                result.set(field.getName(), NumberValue.of((T) field.get(type)));
            } catch (IllegalAccessException ignore) {
            }
        }
        return result;
    }
}