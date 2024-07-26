package com.kingmang.lazurite.libraries.lzr.utils.flatmap;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class LzrFlatmap implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LzrException("TypeExeption ", "Array expected in first argument");
            }
            final Function mapper = ValueUtils.consumeFunction(args[1], 1);
            return flatMapArray((LzrArray) args[0], mapper);
        }

        private LzrValue flatMapArray(LzrArray array, Function mapper) {
            final List<LzrValue> values = new ArrayList<>();
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final LzrValue inner = mapper.execute(array.get(i));
                if (inner.type() != Types.ARRAY) {
                    throw new LzrException("TypeExeption ", "Array expected " + inner);
                }
                for (LzrValue value : (LzrArray) inner) {
                    values.add(value);
                }
            }
            return new LzrArray(values);
        }
    }