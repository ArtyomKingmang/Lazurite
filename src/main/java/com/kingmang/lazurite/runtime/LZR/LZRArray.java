package com.kingmang.lazurite.runtime.LZR;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.Value;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class LZRArray implements Value, Iterable<Value> {

    public static LZRArray of(byte[] array) {
        final int size = array.length;
        final LZRArray result = new LZRArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, LZRNumber.of(array[i]));
        }
        return result;
    }

    public static LZRArray of(String[] array) {
        final int size = array.length;
        final LZRArray result = new LZRArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, new LZRString(array[i]));
        }
        return result;
    }
    
    public static LZRArray add(LZRArray array, Value value) {
        final int last = array.elements.length;
        final LZRArray result = new LZRArray(last + 1);
        System.arraycopy(array.elements, 0, result.elements, 0, last);
        result.elements[last] = value;
        return result;
    }
    
    public static LZRArray merge(LZRArray array1, LZRArray array2) {
        final int length1 = array1.elements.length;
        final int length2 = array2.elements.length;
        final int length = length1 + length2;
        final LZRArray result = new LZRArray(length);
        System.arraycopy(array1.elements, 0, result.elements, 0, length1);
        System.arraycopy(array2.elements, 0, result.elements, length1, length2);
        return result;
    }
    
    public static LZRString joinToString(LZRArray array, String delimiter, String prefix, String suffix) {
        final StringBuilder sb = new StringBuilder();
        for (Value value : array) {
            if (sb.length() > 0) sb.append(delimiter);
            else sb.append(prefix);
            sb.append(value.asString());
        }
        sb.append(suffix);
        return new LZRString(sb.toString());
    }
    
    private final Value[] elements;

    public LZRArray(int size) {
        this.elements = new Value[size];
    }

    public LZRArray(Value[] elements) {
        this.elements = new Value[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }
    
    public LZRArray(List<Value> values) {
        final int size = values.size();
        this.elements = values.toArray(new Value[size]);
    }
    
    public LZRArray(LZRArray array) {
        this(array.elements);
    }

    public Value[] getCopyElements() {
        final Value[] result = new Value[elements.length];
        System.arraycopy(elements, 0, result, 0, elements.length);
        return result;
    }
    
    @Override
    public int type() {
        return Types.ARRAY;
    }

    public int size() {
        return elements.length;
    }

    public Value get(int index) {
        return elements[index];
    }

    public Value get(Value index) {
        final String prop = index.asString();
        switch (prop) {
            // Properties
            case "length":
                return LZRNumber.of(size());

            // Functions
            case "isEmpty":
                return Converters.voidToBoolean(() -> size() == 0);
            case "joinToString":
                return new LZRFunction(this::joinToString);

            default:
                return get(index.asInt());
        }
    }
    
    public Value joinToString(Value[] args) {
        Arguments.checkRange(0, 3, args.length);
        switch (args.length) {
            case 0: 
                return joinToString(this, "", "", "");
            case 1:
                return joinToString(this, args[0].asString(), "", "");
            case 2:
                return joinToString(this, args[0].asString(), args[1].asString(), args[1].asString());
            case 3:
                return joinToString(this, args[0].asString(), args[1].asString(), args[2].asString());
            default:
                throw new LZRExeption("ArgumentsMismatchException","Wrong number of arguments");
        }
    }

    public void set(int index, Value value) {
        elements[index] = value;
    }

    @Override
    public Object raw() {
        return elements;
    }

    @Override
    public int asInt() {
        throw new LZRExeption("TypeExeption","Cannot cast array to integer");
    }

    @Override
    public double asNumber() {
        throw new LZRExeption("TypeExeption","Cannot cast array to number");
    }

    @Override
    public String asString() {
        return Arrays.toString(elements);
    }

    @Override
    public Iterator<Value> iterator() {
        return Arrays.asList(elements).iterator();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Arrays.deepHashCode(this.elements);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final LZRArray other = (LZRArray) obj;
        return Arrays.deepEquals(this.elements, other.elements);
    }

    @Override
    public int compareTo(Value o) {
        if (o.type() == Types.ARRAY) {
            final int lengthCompare = Integer.compare(size(), ((LZRArray) o).size());
            if (lengthCompare != 0) return lengthCompare;
        }
        return asString().compareTo(o.asString());
    }

    @Override
    public String toString() {
        return asString();
    }
}
