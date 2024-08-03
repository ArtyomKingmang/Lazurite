package com.kingmang.lazurite.libraries.lzr.utils.time;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class time implements Library {
    @Override
    public void init() {
        Variables.define("time", time());
    }

    @Override
    public Map<String, Pair<Integer, Function>> provides() {
        Map<String, Pair<Integer, Function>> map = new HashMap<>();
        map.put("time", new Pair<>(Types.MAP, args -> time()));
        return map;
    }

    private static LzrMap time() {
        LzrMap time = new LzrMap(1);
        time.set("sleep", (@NotNull LzrValue... args) -> {
            Arguments.check(1, args.length);
            try {
                Thread.sleep((long) args[0].asNumber());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return LzrNumber.MINUS_ONE;
        });
        return time;
    }
}
