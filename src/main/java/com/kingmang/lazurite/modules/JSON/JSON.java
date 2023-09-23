package com.kingmang.lazurite.modules.JSON;

import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.Value;
import org.json.JSONException;
import org.json.JSONTokener;


public final class JSON implements Module {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("JSONDecode", new DECODE());
    }

    private final class DECODE implements Function {

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
    }


}

