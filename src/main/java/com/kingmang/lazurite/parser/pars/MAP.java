package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.base.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.base.ValueUtils;
import java.util.Map;

public final class MAP implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final Value container = args[0];
        if (container.type() == Types.ARRAY) {
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return mapArray((LZRArray) container, mapper);
        }
        
        if (container.type() == Types.MAP) {
            final Function keyMapper = ValueUtils.consumeFunction(args[1], 1);
            final Function valueMapper = ValueUtils.consumeFunction(args[2], 2);
            return mapMap((LZRMap) container, keyMapper, valueMapper);
        }

        throw new LZRExeption("ArgumentsMismatchException ","Invalid first argument. Array or map expected");
    }
    
    private Value mapArray(LZRArray array, Function mapper) {
        final int size = array.size();
        final LZRArray result = new LZRArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, mapper.execute(array.get(i)));
        }
        return result;
    }
    
    private Value mapMap(LZRMap map, Function keyMapper, Function valueMapper) {
        final LZRMap result = new LZRMap(map.size());
        for (Map.Entry<Value, Value> element : map) {
            final Value newKey = keyMapper.execute(element.getKey());
            final Value newValue = valueMapper.execute(element.getValue());
            result.set(newKey, newValue);
        }
        return result;
    }
}
