package com.kingmang.lazurite.libraries.arrays;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.utils.ValueUtils;


public final class arrays implements Library {
    @Override
    public void init() {
        LzrMap array = new LzrMap(4);
        array.set("join", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.checkRange(1, 4, args.length);
                if (args[0].type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ","Array expected in first argument");
                }

                final LzrArray array = (LzrArray) args[0];
                switch (args.length) {
                    case 1:
                        return LzrArray.joinToString(array, "", "", "");
                    case 2:
                        return LzrArray.joinToString(array, args[1].asString(), "", "");
                    case 3:
                        return LzrArray.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
                    case 4:
                        return LzrArray.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
                    default:
                        throw new LZRException("ArgumentsMismatchException ","Wrong number of arguments");
                }
            }
        });
        array.set("sort", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.checkAtLeast(1, args.length);
                if (args[0].type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ","Array expected in first argument");
                }
                final Value[] elements = ((LzrArray) args[0]).getCopyElements();

                switch (args.length) {
                    case 1:
                        java.util.Arrays.sort(elements);
                        break;
                    case 2:
                        final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                        java.util.Arrays.sort(elements, (o1, o2) -> comparator.execute(o1, o2).asInt());
                        break;
                    default:
                        throw new LZRException("ArgumentsMismatchException ","Wrong number of arguments");
                }

                return new LzrArray(elements);
            }

        });
        array.set("combine", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(2, args.length);
                if (args[0].type() != Types.ARRAY) {
                    throw new LZRException("TypeException","Array expected in first argument");
                }
                if (args[1].type() != Types.ARRAY) {
                    throw new LZRException("TypeException", "Array expected in second argument");
                }

                final LzrArray keys = ((LzrArray) args[0]);
                final LzrArray values = ((LzrArray) args[1]);
                final int length = Math.min(keys.size(), values.size());

                final LzrMap result = new LzrMap(length);
                for (int i = 0; i < length; i++) {
                    result.set(keys.get(i), values.get(i));
                }
                return result;
            }

        });
        array.set("keyExists", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(2, args.length);
                if (args[1].type() != Types.MAP) {
                    throw new LZRException("TypeException","Map expected in second argument");
                }
                final LzrMap map = ((LzrMap) args[1]);
                return LzrNumber.fromBoolean(map.containsKey(args[0]));
            }
        });
        Variables.define("arrays", array);

    }


}
