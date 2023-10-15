package com.kingmang.lazurite.libraries.Arrays;
import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.core.*;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;


public final class Arrays implements Library {


    @Override
    public void init() {
        KEYWORD.put("Array", new newA());
        KEYWORD.put("join", new JOIN());
        KEYWORD.put("sort", new SORT());
        KEYWORD.put("ArrayCombine", new arrayCombine());
        KEYWORD.put("ArrayKeyExists", new arrayKeyExists());

    }

    private final class arrayCombine implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRExeption("TypeException","Array expected in first argument");
            }
            if (args[1].type() != Types.ARRAY) {
                throw new LZRExeption("TypeException", "Array expected in second argument");
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

    }
    public final class arrayKeyExists implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[1].type() != Types.MAP) {
                throw new LZRExeption("TypeException","Map expected in second argument");
            }
            final LZRMap map = ((LZRMap) args[1]);
            return LZRNumber.fromBoolean(map.containsKey(args[0]));
        }

    }
    private final class SORT implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRExeption("TypeExeption ","Array expected in first argument");
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
                    throw new LZRExeption("ArgumentsMismatchException ","Wrong number of arguments");
            }

            return new LZRArray(elements);
        }

    }

    private final class JOIN implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkRange(1, 4, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LZRExeption("TypeExeption ","Array expected in first argument");
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
                    throw new LZRExeption("ArgumentsMismatchException ","Wrong number of arguments");
            }
        }
    }
    private final class newA implements Function {

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
}
