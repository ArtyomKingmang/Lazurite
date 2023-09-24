package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.core.ValueUtils;
import java.util.ArrayList;
import java.util.List;

public final class FLATMAP implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new LZRExeption("TypeExeption ", "Array expected in first argument");
        }
        final Function mapper = ValueUtils.consumeFunction(args[1], 1);
        return flatMapArray((LZRArray) args[0], mapper);
    }
    
    private Value flatMapArray(LZRArray array, Function mapper) {
        final List<Value> values = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final Value inner = mapper.execute(array.get(i));
            if (inner.type() != Types.ARRAY) {
                throw new LZRExeption("TypeExeption ", "Array expected " + inner);
            }
            for (Value value : (LZRArray) inner) {
                values.add(value);
            }
        }
        return new LZRArray(values);
    }
}
