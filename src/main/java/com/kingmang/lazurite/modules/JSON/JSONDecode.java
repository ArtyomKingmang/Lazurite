package com.kingmang.lazurite.modules.JSON;

import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.base.ValueUtils;
import org.json.JSONException;
import org.json.JSONTokener;

public final class JSONDecode implements Function {

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
