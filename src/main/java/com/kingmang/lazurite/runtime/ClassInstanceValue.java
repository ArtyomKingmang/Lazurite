package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ClassInstanceValue implements LzrValue {
    @Getter
    private final String className;
    @Getter
    private final LzrMap thisMap;
    private ClassMethod constructor;
    private UserDefinedFunction toString;

    public ClassInstanceValue(String name) {
        this.className = name;
        thisMap = new LzrMap(10);
    }

    public void addField(String name, LzrValue value) {
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


    public void callConstructor(LzrValue[] args) {
        if (constructor != null) {
            constructor.execute(args);
        }
    }

    public LzrValue access(LzrValue value) {
        return thisMap.get(value);
    }

    public void set(LzrValue key, LzrValue value) {
        final LzrValue v = thisMap.get(key);
        if (v == null) {
            throw new LzrException("RuntimeException ", "Unable to add new field "
                    + key.asString() + " to class " + className);
        }
        thisMap.set(key, value);
    }

    @Nullable
    @Override
    public Object raw() {
        return null;
    }

    @Override
    public int asInt() {
        throw new LzrException("TypeExeption","Cannot cast class to integer");
    }

    @Override
    public double asNumber() {
        throw new LzrException("TypeExeption","Cannot cast class to integer");
    }

    @NotNull
    @Override
    public String asString() {
        if (toString != null) {
            return toString.execute().asString();
        }
        return className + "@" + thisMap;
    }

    @NotNull
    @Override
    public int[] asArray() {
        return new int[0];
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
    public int compareTo(@NotNull LzrValue o) {
        return asString().compareTo(o.asString());
    }

    @Override
    public String toString() {
        return asString();
    }
}
