package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;

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
            return filterArray((LZRArray) container, predicate, takeWhile);
        }
        
        if (container.type() == Types.MAP) {
            return filterMap((LZRMap) container, predicate, takeWhile);
        }

        throw new LZRExeption("TypeExeption", "Invalid first argument. Array or map expected");
    }
    
    private Value filterArray(LZRArray array, Function predicate, boolean takeWhile) {
        final int size = array.size();
        final List<Value> values = new ArrayList<>(size);
        for (Value value : array) {
            if (predicate.execute(value) != LZRNumber.ZERO) {
                values.add(value);
            } else if (takeWhile) break;
        }
        final int newSize = values.size();
        return new LZRArray(values.toArray(new Value[newSize]));
    }
    
    private Value filterMap(LZRMap map, Function predicate, boolean takeWhile) {
        final LZRMap result = new LZRMap(map.size());
        for (Map.Entry<Value, Value> element : map) {
            if (predicate.execute(element.getKey(), element.getValue()) != LZRNumber.ZERO) {
                result.set(element.getKey(), element.getValue());
            } else if (takeWhile) break;
        }
        return result;
    }
}
