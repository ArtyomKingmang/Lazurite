package com.kingmang.lazurite.libraries.lzr.utils.json;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.utils.ValueUtils;
import kotlin.Pair;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public final class json implements Library {
    @Override
    public void init() {
        Variables.define("json", json());
    }

    @Override
    public Map<String, Pair<Integer, Function>> provides() {
        Map<String, Pair<Integer, Function>> map = new HashMap<>();
        map.put("json", new Pair<>(Types.MAP, args -> json()));
        return map;
    }

    private static LzrMap json() {
        LzrMap json = new LzrMap(2);
        json.set("decode", args -> {
            Arguments.check(1, args.length);
            try {
                final String jsonRaw = args[0].asString();
                final Object root = new JSONTokener(jsonRaw).nextValue();
                return ValueUtils.toValue(root);
            } catch (JSONException ex) {
                throw new RuntimeException("Error while parsing json", ex);
            }
        });
        return json;
    }
}

