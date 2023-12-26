package com.kingmang.lazurite.parser.Standart;

import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.core.ValueUtils;
import java.util.Map;

public final class reduce implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(3, args.length);
        
        final Value container = args[0];
        final Value identity = args[1];
        final Function accumulator = ValueUtils.consumeFunction(args[2], 2);
        if (container.type() == Types.ARRAY) {
            Value result = identity;
            final LZRArray array = (LZRArray) container;
            for (Value element : array) {
                result = accumulator.execute(result, element);
            }
            return result;
        }
        if (container.type() == Types.MAP) {
            Value result = identity;
            final LZRMap map = (LZRMap) container;
            for (Map.Entry<Value, Value> element : map) {
                result = accumulator.execute(result, element.getKey(), element.getValue());
            }
            return result;
        }
        throw new LZRException("TypeExeption", "Invalid first argument. Array or map expected");
    }
}
