package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.LZREx.LZRExeption;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class reference implements Value, Serializable {

    private Object ref;

    public reference(Object ref) {
        this.ref = ref;
    }

    @Override
    public int asInt() {
        throw new LZRExeption("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @Override
    public double asNumber() {
        throw new LZRExeption("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
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