package com.kingmang.lazurite.core;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.runtime.ClassInstanceValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class Classes {

    @Getter
    private static final Map<String, ClassInstanceValue> classes;
    static {
        classes = new HashMap<>();
    }

    private Classes() { }

    public static void clear() {
        classes.clear();
    }

    public static boolean isExists(String key) {
        return classes.containsKey(key);
    }
    
    public static ClassInstanceValue get(String key) {
        if (!isExists(key)) throw new LZRException("UnknownClassException ", "Unknown class " + key);
        return classes.get(key);
    }
    
    public static void set(String key, ClassInstanceValue classDef) {
        classes.put(key, classDef);
    }
}
