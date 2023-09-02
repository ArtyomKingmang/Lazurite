package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.lib._TExeprion;
import com.kingmang.lazurite.lib.Arguments;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.MapValue;
import com.kingmang.lazurite.lib.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.lib.ValueUtils;
import java.util.Map;

public final class MAP implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final Value container = args[0];
        if (container.type() == Types.ARRAY) {
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return mapArray((ArrayValue) container, mapper);
        }
        
        if (container.type() == Types.MAP) {
            final Function keyMapper = ValueUtils.consumeFunction(args[1], 1);
            final Function valueMapper = ValueUtils.consumeFunction(args[2], 2);
            return mapMap((MapValue) container, keyMapper, valueMapper);
        }

        throw new _TExeprion("Invalid first argument. Array or map expected");
    }
    
    private Value mapArray(ArrayValue array, Function mapper) {
        final int size = array.size();
        final ArrayValue result = new ArrayValue(size);
        for (int i = 0; i < size; i++) {
            result.set(i, mapper.execute(array.get(i)));
        }
        return result;
    }
    
    private Value mapMap(MapValue map, Function keyMapper, Function valueMapper) {
        final MapValue result = new MapValue(map.size());
        for (Map.Entry<Value, Value> element : map) {
            final Value newKey = keyMapper.execute(element.getKey());
            final Value newValue = valueMapper.execute(element.getValue());
            result.set(newKey, newValue);
        }
        return result;
    }
}
