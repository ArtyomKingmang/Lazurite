package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.lib.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FILTER implements Function {

    private final boolean takeWhile;

    public FILTER(boolean takeWhile) {
        this.takeWhile = takeWhile;
    }

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        final Value container = args[0];
        final Function predicate = ValueUtils.consumeFunction(args[1], 1);
        if (container.type() == Types.ARRAY) {
            return filterArray((ArrayValue) container, predicate, takeWhile);
        }
        
        if (container.type() == Types.MAP) {
            return filterMap((MapValue) container, predicate, takeWhile);
        }

        throw new _TExeprion("Invalid first argument. Array or map expected");
    }
    
    private Value filterArray(ArrayValue array, Function predicate, boolean takeWhile) {
        final int size = array.size();
        final List<Value> values = new ArrayList<>(size);
        for (Value value : array) {
            if (predicate.execute(value) != NumberValue.ZERO) {
                values.add(value);
            } else if (takeWhile) break;
        }
        final int newSize = values.size();
        return new ArrayValue(values.toArray(new Value[newSize]));
    }
    
    private Value filterMap(MapValue map, Function predicate, boolean takeWhile) {
        final MapValue result = new MapValue(map.size());
        for (Map.Entry<Value, Value> element : map) {
            if (predicate.execute(element.getKey(), element.getValue()) != NumberValue.ZERO) {
                result.set(element.getKey(), element.getValue());
            } else if (takeWhile) break;
        }
        return result;
    }
}
