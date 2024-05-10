package com.kingmang.lazurite.runtime.specification;

import java.util.HashMap;
import java.util.Map;

public class Specifications {

    private static Map<String, Specification> specs = new HashMap<>();

    public static void put(String name, Specification spec) {
        specs.put(name, spec);
    }

    public static Specification get(String name) {
        return specs.get(name);
    }

    public static void remove(String name) {
        specs.remove(name);
    }

}
