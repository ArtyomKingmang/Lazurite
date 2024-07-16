package com.kingmang.lazurite.runtime.values;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LzrFunction implements LzrValue {

    public static final LzrFunction EMPTY = new LzrFunction(args -> LzrNumber.ZERO);

    @Getter
    @NotNull
    public final Function value;

    public LzrFunction(@NotNull Function value) {
        this.value = value;
    }

    @Override
    public int type() {
        return Types.FUNCTION;
    }

    @NotNull
    @Override
    public Function raw() {
        return value;
    }

    @Override
    public int asInt() {
        throw new LzrException("TypeExeption","Cannot cast function to integer");
    }

    @Override
    public double asNumber() {
        throw new LzrException("TypeExeption","Cannot cast function to number");
    }

    @NotNull
    @Override
    public String asString() {
        return value.toString();
    }

    @NotNull
    @Override
    public int[] asArray() {
        return new int[0];
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.value);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final LzrFunction other = (LzrFunction) obj;
        return Objects.equals(this.value, other.value);
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
