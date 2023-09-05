package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.LZREx.LzrExeption;
import com.kingmang.lazurite.base.Types;

import java.util.Objects;

public class ClassInstanceValue implements Value {
    
    private final String className;
    private final MapValue thisMap;
    private ClassMethod constructor;
    private UserDefinedFunction toString;

    public ClassInstanceValue(String name) {
        this.className = name;
        thisMap = new MapValue(10);
    }

    public MapValue getThisMap() {
        return thisMap;
    }

    public String getClassName() {
        return className;
    }

    public void addField(String name, Value value) {
        thisMap.set(name, value);
    }

    public void addMethod(String name, ClassMethod method) {
        if (name.equals("toString")) {
            toString = method;
        }
        thisMap.set(name, method);
        if (name.equals(className)) {
            constructor = method;
        }
    }


    public void callConstructor(Value[] args) {
        if (constructor != null) {
            constructor.execute(args);
        }
    }

    public Value access(Value value) {
        return thisMap.get(value);
    }

    public void set(Value key, Value value) {
        final Value v = thisMap.get(key);
        if (v == null) {
            throw new RuntimeException("Unable to add new field "
                    + key.asString() + " to class " + className);
        }
        thisMap.set(key, value);
    }

    @Override
    public Object raw() {
        return null;
    }

    @Override
    public int asInt() {
        throw new LzrExeption("TypeExeption","Cannot cast class to integer");
    }

    @Override
    public double asNumber() {
        throw new LzrExeption("TypeExeption","Cannot cast class to integer");
    }

    @Override
    public String asString() {
        if (toString != null) {
            return toString.execute(new Value[] {}).asString();
        }
        return className + "@" + thisMap;
    }

    @Override
    public int type() {
        return Types.CLASS;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hash(className, thisMap);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final ClassInstanceValue other = (ClassInstanceValue) obj;
        return Objects.equals(this.className, other.className)
                && Objects.equals(this.thisMap, other.thisMap);
    }

    @Override
    public int compareTo(Value o) {
        return asString().compareTo(o.asString());
    }

    @Override
    public String toString() {
        return asString();
    }
}
