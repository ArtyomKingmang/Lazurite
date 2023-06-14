package com.kingmang.lazurite.modules.Arrays;

import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

public final class NEWARR implements Function {

    @Override
    public Value execute(Value... args) {
        return createArray(args, 0);
    }

    private ArrayValue createArray(Value[] args, int index) {
        final int size = args[index].asInt();
        final int last = args.length - 1;
        ArrayValue array = new ArrayValue(size);
        if (index == last) {
            for (int i = 0; i < size; i++) {
                array.set(i, NumberValue.ZERO);
            }
        } else if (index < last) {
            for (int i = 0; i < size; i++) {
                array.set(i, createArray(args, index + 1));
            }
        }
        return array;
    }
}