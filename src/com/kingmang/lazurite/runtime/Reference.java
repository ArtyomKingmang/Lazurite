package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.exceptions.LZRException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Reference implements Value, Serializable {

    private Object ref;

    public Reference(Object ref) {
        this.ref = ref;
    }

    @Override
    public int asInt() {
        throw new LZRException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @Override
    public double asNumber() {
        throw new LZRException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @Override
    public Object raw() {
        return ref;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 0;
    }

    public Object getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "#Reference<" + hashCode() + ">";
    }

    @Override
    public int compareTo(@NotNull Value o) {
        return 0;
    }
}