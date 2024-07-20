package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Reference implements LzrValue, Serializable {
    private Object ref;

    @Override
    public int asInt() {
        throw new LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @Override
    public double asNumber() {
        throw new LzrException("BadArithmetic", "Cannot cast REFERENCE to a NUMBER");
    }

    @NotNull
    @Override
    public Object raw() {
        return ref;
    }

    @NotNull
    @Override
    public String asString() {
        return "#Reference<" + hashCode() + ">";
    }

    @NotNull
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
        return asString();
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        return 0;
    }
}