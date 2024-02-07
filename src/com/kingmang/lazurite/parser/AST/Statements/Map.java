package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.utils.ValueUtils;

public final class Map implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final Value container = args[0];
        if (container.type() == Types.ARRAY) {
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return mapArray((LzrArray) container, mapper);
        }
        
        if (container.type() == Types.MAP) {
            final Function keyMapper = ValueUtils.consumeFunction(args[1], 1);
            final Function valueMapper = ValueUtils.consumeFunction(args[2], 2);
            return mapMap((LzrMap) container, keyMapper, valueMapper);
        }

        throw new LZRException("ArgumentsMismatchException ","Invalid first argument. Array or map expected");
    }
    
    private Value mapArray(LzrArray array, Function mapper) {
        final int size = array.size();
        final LzrArray result = new LzrArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, mapper.execute(array.get(i)));
        }
        return result;
    }
    
    private Value mapMap(LzrMap map, Function keyMapper, Function valueMapper) {
        final LzrMap result = new LzrMap(map.size());
        for (java.util.Map.Entry<Value, Value> element : map) {
            final Value newKey = keyMapper.execute(element.getKey());
            final Value newValue = valueMapper.execute(element.getValue());
            result.set(newKey, newValue);
        }
        return result;
    }
}
