package com.kingmang.lazurite.core;

import com.kingmang.lazurite.LZREx.LZRException;

import java.util.HashMap;
import java.util.Map;


public final class KEYWORD {

    private static final Map<String, Function> functions;
    static {
        functions = new HashMap<>();
    }

    private KEYWORD() { }

    public static void clear() {
        functions.clear();
    }

    public static Map<String, Function> getFunctions() {
        return functions;
    }
    
    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new LZRException("UnknownFunctionException ", "Unknown function " + key);
        return functions.get(key);
    }
    
    public static void put(String key, Function function) {
        functions.put(key, function);
    }

    public static void define(String key, Function function) {
        if (isExists(key)) throw new LZRException("DuplicateFunctionException ", "Function " + key + " already exists");
        put(key, function);
    }
}
