package com.kingmang.lazurite.runtime.LZR;

import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.Value;

import java.util.Objects;


public final class LZRString implements Value {
    
    public static final LZRString EMPTY = new LZRString("");
    
    private final String value;

    public LZRString(String value) {
        this.value = value;
    }


    public Value access(Value propertyValue) {
        final String prop = propertyValue.asString();
        switch (prop) {
            // Properties
            case "length":
                return LZRNumber.of(length());
            case "lower":
                return new LZRString(value.toLowerCase());
            case "upper":
                return new LZRString(value.toUpperCase());
            case "chars": {
                final Value[] chars = new Value[length()];
                int i = 0;
                for (char ch : value.toCharArray()) {
                    chars[i++] = LZRNumber.of((int) ch);
                }
                return new LZRArray(chars);
            }

            // Functions
            case "trim":
                return Converters.voidToString(value::trim);
            case "startsWith":
                return new LZRFunction(args -> {
                    Arguments.checkOrOr(1, 2, args.length);
                    int offset = (args.length == 2) ? args[1].asInt() : 0;
                    return LZRNumber.fromBoolean(value.startsWith(args[0].asString(), offset));
                });
            case "endsWith":
                return Converters.stringToBoolean(value::endsWith);
            case "matches":
                return Converters.stringToBoolean(value::matches);
            case "contains":
                return Converters.stringToBoolean(value::contains);
            case "equalsIgnoreCase":
                return Converters.stringToBoolean(value::equalsIgnoreCase);
            case "isEmpty":
                return Converters.voidToBoolean(value::isEmpty);

            default:
                if (KEYWORD.isExists(prop)) {
                    final Function f = KEYWORD.get(prop);
                    return new LZRFunction(args -> {
                        final Value[] newArgs = new Value[args.length + 1];
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
        final LZRString other = (LZRString) obj;
        return Objects.equals(this.value, other.value);
    }
    
    @Override
    public int compareTo(Value o) {
        if (o.type() == Types.STRING) {
            return value.compareTo(((LZRString) o).value);
        }
        return asString().compareTo(o.asString());
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
