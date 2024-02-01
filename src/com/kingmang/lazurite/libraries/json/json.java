package com.kingmang.lazurite.libraries.json;

import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.utils.ValueUtils;
import org.json.JSONException;
import org.json.JSONTokener;


public final class json implements Library {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        LzrMap json = new LzrMap(2);
        json.set("decode", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(1, args.length);
                try {
                    final String jsonRaw = args[0].asString();
                    final Object root = new JSONTokener(jsonRaw).nextValue();
                    return ValueUtils.toValue(root);
                } catch (JSONException ex) {
                    throw new RuntimeException("Error while parsing json", ex);
                }
            }
        });
        Variables.define("json", json);
    }


}

