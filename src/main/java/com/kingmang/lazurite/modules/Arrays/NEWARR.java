package com.kingmang.lazurite.modules.Arrays;

import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

public final class NEWARR implements Function {

    @Override
    public Value execute(Value... args) {
        return createArray(args, 0);
    }

    private LZRArray createArray(Value[] args, int index) {
        final int size = args[index].asInt();
        final int last = args.length - 1;
        LZRArray array = new LZRArray(size);
        if (index == last) {
            for (int i = 0; i < size; i++) {
                array.set(i, LZRNumber.ZERO);
            }
        } else if (index < last) {
            for (int i = 0; i < size; i++) {
                array.set(i, createArray(args, index + 1));
            }
        }
        return array;
    }
}