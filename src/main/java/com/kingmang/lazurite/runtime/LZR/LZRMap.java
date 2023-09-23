package com.kingmang.lazurite.runtime.LZR;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.runtime.Value;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;


public class LZRMap implements Value, Iterable<Map.Entry<Value, Value>> {
    
    public static final LZRMap EMPTY = new LZRMap(1);
    
    public static LZRMap merge(LZRMap map1, LZRMap map2) {
        final LZRMap result = new LZRMap(map1.size() + map2.size());
        result.map.putAll(map1.map);
        result.map.putAll(map2.map);
        return result;
    }

    private final Map<Value, Value> map;

    public LZRMap(int size) {
        this.map = new LinkedHashMap<>(size);
    }

    public LZRMap(Map<Value, Value> map) {
        this.map = map;
    }

    public boolean ifPresent(String key, Consumer<Value> consumer) {
        return ifPresent(new LZRString(key), consumer);
    }

    public boolean ifPresent(Value key, Consumer<Value> consumer) {
        if (map.containsKey(key)) {
            consumer.accept(map.get(key));
            return true;
        }
        return false;
    }

    public LZRArray toPairs() {
        final int size = map.size();
        final LZRArray result = new LZRArray(size);
        int index = 0;
        for (Map.Entry<Value, Value> entry : map.entrySet()) {
            result.set(index++, new LZRArray(new Value[] {
                entry.getKey(), entry.getValue()
            }));
        }
        return result;
    }
    
    @Override
    public int type() {
        return Types.MAP;
    }
    
    public int size() {
        return map.size();
    }
    
    public boolean containsKey(Value key) {
        return map.containsKey(key);
    }

    public Value get(Value key) {
        return map.get(key);
    }

    public void set(String key, Value value) {
        set(new LZRString(key), value);
    }

    public void set(String key, Function function) {
        set(new LZRString(key), new LZRFunction(function));
    }
    
    public void set(Value key, Value value) {
        map.put(key, value);
    }

    public Map<Value, Value> getMap() {
        return map;
    }
    
    @Override
    public Object raw() {
        return map;
    }
    
    @Override
    public int asInt() {
        throw new LZRExeption("TypeExeprion","Cannot cast map to integer");
    }

    @Override
    public double asNumber() {
        throw new LZRExeption("TypeExeprtion","Cannot cast map to number");
    }

    @Override
    public String asString() {
        return map.toString();
    }

    @Override
    public Iterator<Map.Entry<Value, Value>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.map);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final LZRMap other = (LZRMap) obj;
        return Objects.equals(this.map, other.map);
    }
    
    @Override
    public int compareTo(Value o) {
        if (o.type() == Types.MAP) {
            final int lengthCompare = Integer.compare(size(), ((LZRMap) o).size());
            if (lengthCompare != 0) return lengthCompare;
        }
        return asString().compareTo(o.asString());
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
