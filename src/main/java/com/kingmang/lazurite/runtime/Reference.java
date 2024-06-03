package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.exceptions.LzrException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@AllArgsConstructor
public class Reference implements LzrValue, Serializable {

    @Getter
    private Object ref;
    @Override
    public int asInt() {
        throw new LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @Override
    public double asNumber() {
        throw new LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
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


    @Override
    public String toString() {
        return "#Reference<" + hashCode() + ">";
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        return 0;
    }
}