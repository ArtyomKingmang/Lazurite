package com.kingmang.lazurite.libraries.arrays;
import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.*;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;


public final class arrays implements Library {


    @Override
    public void init() {
        LZRMap array = new LZRMap(4);
        array.set("join", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.checkRange(1, 4, args.length);
                if (args[0].type() != Types.ARRAY) {
                    throw new LZRException("TypeExeption ","Array expected in first argument");
                }

                final LZRArray array = (LZRArray) args[0];
                switch (args.length) {
                    case 1:
                        return LZRArray.joinToString(array, "", "", "");
                    case 2:
                        return LZRArray.joinToString(array, args[1].asString(), "", "");
                    case 3:
                        return LZRArray.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
                    case 4:
                        return LZRArray.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
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
                final Value[] elements = ((LZRArray) args[0]).getCopyElements();

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

                return new LZRArray(elements);
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

                final LZRArray keys = ((LZRArray) args[0]);
                final LZRArray values = ((LZRArray) args[1]);
                final int length = Math.min(keys.size(), values.size());

                final LZRMap result = new LZRMap(length);
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
                final LZRMap map = ((LZRMap) args[1]);
                return LZRNumber.fromBoolean(map.containsKey(args[0]));
            }
        });
        Variables.define("arrays", array);

    }




}
