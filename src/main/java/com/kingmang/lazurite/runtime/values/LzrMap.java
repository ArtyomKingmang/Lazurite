package com.kingmang.lazurite.runtime.values;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class LzrMap implements LzrValue, Iterable<Map.Entry<LzrValue, LzrValue>> {
    
    public static final LzrMap EMPTY = new LzrMap(1);

    public static LzrMap merge(LzrMap map1, LzrMap map2) {
        final LzrMap result = new LzrMap(map1.size() + map2.size());
        result.map.putAll(map1.map);
        result.map.putAll(map2.map);
        return result;
    }
    private final Map<LzrValue, LzrValue> map;

    public LzrMap(int size) {
        this.map = new LinkedHashMap<>(size);
    }

    public boolean ifPresent(String key, Consumer<LzrValue> consumer) {
        return ifPresent(new LzrString(key), consumer);
    }

    public boolean ifPresent(LzrValue key, Consumer<LzrValue> consumer) {
        if (map.containsKey(key)) {
            consumer.accept(map.get(key));
            return true;
        }
        return false;
    }

    public LzrArray toPairs() {
        final int size = map.size();
        final LzrArray result = new LzrArray(size);
        int index = 0;
        for (Map.Entry<LzrValue, LzrValue> entry : map.entrySet()) {
            result.set(index++, new LzrArray(new LzrValue[] {
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
    
    public boolean containsKey(LzrValue key) {
        return map.containsKey(key);
    }

    public LzrValue get(LzrValue key) {
        return map.get(key);
    }

    public void set(String key, LzrValue value) {
        set(new LzrString(key), value);
    }

    public void set(String key, Function function) {
        set(new LzrString(key), new LzrFunction(function));
    }
    
    public void set(LzrValue key, LzrValue value) {
        map.put(key, value);
    }


    
    @Override
    public Object raw() {
        return map;
    }
    
    @Override
    public int asInt() {
        throw new LzrException("TypeExeprion","Cannot cast map to integer");
    }

    @Override
    public double asNumber() {
        throw new LzrException("TypeExeprtion","Cannot cast map to number");
    }

    @Override
    public String asString() {
        return map.toString();
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }


    @Override
    public Iterator<Map.Entry<LzrValue, LzrValue>> iterator() {
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
        final LzrMap other = (LzrMap) obj;
        return Objects.equals(this.map, other.map);
    }
    
    @Override
    public int compareTo(LzrValue o) {
        if (o.type() == Types.MAP) {
            final int lengthCompare = Integer.compare(size(), ((LzrMap) o).size());
            if (lengthCompare != 0) return lengthCompare;
        }
        return asString().compareTo(o.asString());
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
