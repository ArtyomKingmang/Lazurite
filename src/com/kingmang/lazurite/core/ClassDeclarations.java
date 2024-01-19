package com.kingmang.lazurite.core;

import com.kingmang.lazurite.parser.AST.Statements.ClassDeclarationStatement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClassDeclarations {

    private static final Map<String, ClassDeclarationStatement> declarations;
    static {
        declarations = new ConcurrentHashMap<>();
    }

    private ClassDeclarations() { }

    public static void clear() {
        declarations.clear();
    }

    public static Map<String, ClassDeclarationStatement> getAll() {
        return declarations;
    }
    
    public static ClassDeclarationStatement get(String key) {
        return declarations.get(key);
    }
    
    public static void set(String key, ClassDeclarationStatement classDef) {
        declarations.put(key, classDef);
    }
}
