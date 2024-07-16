package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;
import org.jetbrains.annotations.NotNull;

public final class Map implements Function {

    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final LzrValue container = args[0];
        if (container.type() == Types.ARRAY) {
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return mapArray((LzrArray) container, mapper);
        }
        
        if (container.type() == Types.MAP) {
            final Function keyMapper = ValueUtils.consumeFunction(args[1], 1);
            final Function valueMapper = ValueUtils.consumeFunction(args[2], 2);
            return mapMap((LzrMap) container, keyMapper, valueMapper);
        }

        throw new LzrException("ArgumentsMismatchException ","Invalid first argument. Array or map expected");
    }

    @NotNull
    private LzrValue mapArray(@NotNull LzrArray array, @NotNull Function mapper) {
        final int size = array.size();
        return new LzrArray(size, index -> mapper.execute(array.get(index)));
    }
    
    private LzrValue mapMap(LzrMap map, Function keyMapper, Function valueMapper) {
        final LzrMap result = new LzrMap(map.size());
        for (java.util.Map.Entry<LzrValue, LzrValue> element : map) {
            final LzrValue newKey = keyMapper.execute(element.getKey());
            final LzrValue newValue = valueMapper.execute(element.getValue());
            result.set(newKey, newValue);
        }
        return result;
    }
}
