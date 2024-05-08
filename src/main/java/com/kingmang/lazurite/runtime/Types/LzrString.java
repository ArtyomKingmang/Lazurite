package com.kingmang.lazurite.runtime.Types;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.runtime.LzrValue;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public final class LzrString implements LzrValue {
    
    public static final LzrString EMPTY = new LzrString("");
    
    private final String value;


    public LzrValue access(LzrValue propertyValue) {
        final String prop = propertyValue.asString();
        switch (prop) {
            case "length":
                return LzrNumber.of(length());
            case "toLowerCase":
                return new LzrString(value.toLowerCase());
            case "toUpperCase":
                return new LzrString(value.toUpperCase());
            case "chars": {
                final LzrValue[] chars = new LzrValue[length()];
                int i = 0;
                for (char ch : value.toCharArray()) {
                    chars[i++] = LzrNumber.of((int) ch);
                }
                return new LzrArray(chars);
            }

            case "trim":
                return Converters.voidToString(value::trim);
            case "startsWith":
                return new LzrFunction(args -> {
                    Arguments.checkOrOr(1, 2, args.length);
                    int offset = (args.length == 2) ? args[1].asInt() : 0;
                    return LzrNumber.fromBoolean(value.startsWith(args[0].asString(), offset));
                });
            case "endsWith":
                return Converters.stringToBoolean(value::endsWith);
            case "matches":
                return Converters.stringToBoolean(value::matches);
            case "equals":
                return Converters.stringToBoolean(value::equalsIgnoreCase);
            case "isEmpty":
                return Converters.voidToBoolean(value::isEmpty);

            default:
                if (Keyword.isExists(prop)) {
                    final Function f = Keyword.get(prop);
                    return new LzrFunction(args -> {
                        final LzrValue[] newArgs = new LzrValue[args.length + 1];
                        newArgs[0] = this;
                        System.arraycopy(args, 0, newArgs, 1, args.length);
                        return f.execute(newArgs);
                    });
                }
                break;
        }
        throw new LZRException("UnknownPropertyException ",prop);
    }

    public int length() {
        return value.length();
    }
    
    @Override
    public int type() {
        return Types.STRING;
    }

    @Override
    public Object raw() {
        return value;
    }
    
    @Override
    public int asInt() {
        return Integer.parseInt(value);
    }
    
    @Override
    public double asNumber() {
        return Double.parseDouble(value);
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final LzrString other = (LzrString) obj;
        return Objects.equals(this.value, other.value);
    }
    
    @Override
    public int compareTo(LzrValue o) {
        if (o.type() == Types.STRING) {
            return value.compareTo(((LzrString) o).value);
        }
        return asString().compareTo(o.asString());
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
