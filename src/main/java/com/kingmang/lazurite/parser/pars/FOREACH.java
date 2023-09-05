package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.base.*;
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
                final StringValue string = (StringValue) container;
                if (argsCount == 2) {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new StringValue(String.valueOf(ch)), NumberValue.of(ch));
                    }
                } else {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new StringValue(String.valueOf(ch)));
                    }
                }
                return string;

            case Types.ARRAY:
                final ArrayValue array = (ArrayValue) container;
                if (argsCount == 2) {
                    int index = 0;
                    for (Value element : array) {
                        consumer.execute(element, NumberValue.of(index++));
                    }
                } else {
                    for (Value element : array) {
                        consumer.execute(element);
                    }
                }
                return array;

            case Types.MAP:
                final MapValue map = (MapValue) container;
                for (Map.Entry<Value, Value> element : map) {
                    consumer.execute(element.getKey(), element.getValue());
                }
                return map;

            default:
                throw new LZRExeption("TypeExeption ","Cannot iterate " + Types.typeToString(container.type()));
        }
    }
}
