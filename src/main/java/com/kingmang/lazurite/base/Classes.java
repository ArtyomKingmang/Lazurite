package com.kingmang.lazurite.base;

import com.kingmang.lazurite.LZREx.LzrExeption;
import com.kingmang.lazurite.runtime.ClassInstanceValue;

import java.util.HashMap;
import java.util.Map;

public final class Classes {

    private static final Map<String, ClassInstanceValue> classes;
    static {
        classes = new HashMap<>();
    }

    private Classes() { }

    public static void clear() {
        classes.clear();
    }

    public static Map<String, ClassInstanceValue> getFunctions() {
        return classes;
    }
    
    public static boolean isExists(String key) {
        return classes.containsKey(key);
    }
    
    public static ClassInstanceValue get(String key) {
        if (!isExists(key)) throw new LzrExeption("UnknownClassException ", "Unknown class " + key);
        return classes.get(key);
    }
    
    public static void set(String key, ClassInstanceValue classDef) {
        classes.put(key, classDef);
    }
}
