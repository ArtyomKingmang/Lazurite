package com.kingmang.lazurite.core;

import com.kingmang.lazurite.parser.AST.Statements.ClassDeclarationStatement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public final class ClassDeclarations {

    @Getter
    private static final Map<String, ClassDeclarationStatement> declarations;
    static {
        declarations = new ConcurrentHashMap<>();
    }

    public static void clear() {
        declarations.clear();
    }
    
    public static ClassDeclarationStatement get(String key) {
        return declarations.get(key);
    }
    
    public static void set(String key, ClassDeclarationStatement classDef) {
        declarations.put(key, classDef);
    }
}
