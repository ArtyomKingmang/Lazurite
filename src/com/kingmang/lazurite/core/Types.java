package com.kingmang.lazurite.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Types {

    public static final int
            OBJECT = 0,
            NUMBER = 1,
            STRING = 2,
            ARRAY = 3,
            MAP = 4,
            FUNCTION = 5,
            CLASS = 6;
    
    private static final int FIRST = OBJECT;
    private static final int LAST = CLASS;
    public static final String[] NAMES = {
        "object", "number", "string", "array", "map", "function", "class"
    };
    
    public static String typeToString(int type) {
        if (FIRST <= type && type <= LAST) {
            return NAMES[type];
        }
        return "unknown (" + type + ")";
    }

}
