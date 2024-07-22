package com.kingmang.lazurite.libraries.lzr.utils.streamApi;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;

public class streamApi implements Library {
    @Override
    public void init() {
        Keyword.put("stream", new initStream());
    }

    public static final class initStream implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            Arguments.checkAtLeast(1, args.length);

            final LzrValue value = args[0];
            return switch (value.type()) {
                case Types.MAP -> new LzrStream(((LzrMap) value).toPairs());
                case Types.ARRAY -> new LzrStream((LzrArray) value);
                default -> throw new LzrException("TypeException","Invalid argument. Array or map expected");
            };
        }
    }
}
