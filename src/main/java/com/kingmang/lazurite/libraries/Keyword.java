package com.kingmang.lazurite.libraries;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public final class Keyword {

    @Getter
    private static final Map<String, Function> functions;
    static {
        functions = new HashMap<>();
    }
    public static void clear() {
        functions.clear();
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new LzrException("UnknownFunctionException", "Unknown function " + key);
        return functions.get(key);
    }
    
    public static void put(String key, Function function) {
        functions.put(key, function);
    }

    public static void define(String key, Function function) {
        if (isExists(key)) throw new LzrException("DuplicateFunctionException", "Function " + key + " already exists");
        put(key, function);
    }
}
