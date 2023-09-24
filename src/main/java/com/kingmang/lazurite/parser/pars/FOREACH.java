package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;

import java.util.Map;

public final class FOREACH implements Function {

    private static final int UNKNOWN = -1;

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        final Value container = args[0];
        final Function consumer = ValueUtils.consumeFunction(args[1], 1);
        final int argsCount;
        if (consumer instanceof UserDefinedFunction) {
            argsCount = ((UserDefinedFunction) consumer).getArgsCount();
        } else {
            argsCount = UNKNOWN;
        }

        switch (container.type()) {
            case Types.STRING:
                final LZRString string = (LZRString) container;
                if (argsCount == 2) {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new LZRString(String.valueOf(ch)), LZRNumber.of(ch));
                    }
                } else {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new LZRString(String.valueOf(ch)));
                    }
                }
                return string;

            case Types.ARRAY:
                final LZRArray array = (LZRArray) container;
                if (argsCount == 2) {
                    int index = 0;
                    for (Value element : array) {
                        consumer.execute(element, LZRNumber.of(index++));
                    }
                } else {
                    for (Value element : array) {
                        consumer.execute(element);
                    }
                }
                return array;

            case Types.MAP:
                final LZRMap map = (LZRMap) container;
                for (Map.Entry<Value, Value> element : map) {
                    consumer.execute(element.getKey(), element.getValue());
                }
                return map;

            default:
                throw new LZRExeption("TypeExeption ","Cannot iterate " + Types.typeToString(container.type()));
        }
    }
}
