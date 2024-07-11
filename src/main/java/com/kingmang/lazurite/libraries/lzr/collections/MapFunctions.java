package com.kingmang.lazurite.libraries.lzr.collections;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Supplier;

public class MapFunctions {

    public static Function mapFunction(final Supplier<Map<LzrValue, LzrValue>> mapSupplier) {
        return (args) -> {
            Arguments.checkOrOr(0, 1, args.length);
            final Map<LzrValue, LzrValue> map = mapSupplier.get();
            if (args.length == 1) {
                if (args[0].type() == Types.MAP) {
                    map.putAll(((LzrMap) args[0]).getMap());
                } else {
                    throw new LzrException("TypeException ","Map expected in first argument");
                }
            }
            return new LzrMap(map);
        };
    }

    public static Function sortedMapFunction(final Supplier<SortedMap<LzrValue, LzrValue>> mapSupplier,
                                       final java.util.function.Function<
                                               Comparator<? super LzrValue>,
                                               SortedMap<LzrValue, LzrValue>> comparatorToMapFunction) {
        return (args) -> {
            Arguments.checkRange(0, 2, args.length);
            final SortedMap<LzrValue, LzrValue> map;
            switch (args.length) {
                case 0: // treeMap()
                    map = mapSupplier.get();
                    break;
                case 1: // treeMap(map) || treeMap(comparator)
                    if (args[0].type() == Types.MAP) {
                        map = mapSupplier.get();
                        map.putAll(((LzrMap) args[0]).getMap());
                    } else if (args[0].type() == Types.FUNCTION) {
                        final Function comparator = ValueUtils.consumeFunction(args[0], 0);
                        map = comparatorToMapFunction.apply((o1, o2) -> comparator.execute(o1, o2).asInt());
                    } else {
                        throw new LzrException("TypeException ","Map or comparator function expected in first argument");
                    }
                    break;
                case 2: // treeMap(map, comparator)
                    if (args[0].type() != Types.MAP) {
                        throw new LzrException("TypeException ", "Map expected in first argument");
                    }
                    final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                    map = comparatorToMapFunction.apply((o1, o2) -> comparator.execute(o1, o2).asInt());
                    map.putAll(((LzrMap) args[0]).getMap());
                    break;
                default:
                    throw new IllegalStateException();
            }
            return new LzrMap(map);
        };
    }
}
