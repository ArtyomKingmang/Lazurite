package com.kingmang.lazurite.libraries.lzr.utils.base64;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;
import kotlin.Pair;

import java.nio.charset.StandardCharsets;
import java.sql.Types;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "ClassName"})
public class base64 implements Library {
    private static final int TYPE = 8;

    @Override
    public void init() {
        Variables.define("base64", base64());
    }

    @Override
    public Map<String, Pair<Integer, Function>> provides() {
        Map<String, Pair<Integer, Function>> map = new HashMap<>();
        map.put("json", new Pair<>(com.kingmang.lazurite.core.Types.MAP, args -> base64()));
        return map;
    }

    private LzrMap base64() {
        LzrMap base = new LzrMap(2);
        base.set("encode", this::encode);
        base.set("decode", this::decode);
        return base;
    }

    private LzrValue encode(LzrValue... args) {
        Arguments.checkOrOr(1, 2, args.length);
        return LzrArray.of(enc(args).encode(input(args)));
    }

    private Base64.Encoder enc(LzrValue[] args) {
        if (args.length == 2 && args[1].asInt() == TYPE) {
            return Base64.getUrlEncoder();
        }
        return Base64.getEncoder();
    }

    private byte[] input(LzrValue[] args) {
        byte[] input;
        if (args[0].type() == Types.ARRAY) {
            input = ValueUtils.toByteArray((LzrArray) args[0]);
        } else {
            try {
                input = args[0].asString().getBytes(StandardCharsets.UTF_8);
            } catch (Exception e) {
                input = args[0].asString().getBytes();
            }
        }
        return input;
    }

    private LzrValue decode(LzrValue[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        final Base64.Decoder decoder = getDecoder(args);

        return LzrArray.of(
                args[0].type() == Types.ARRAY ?
                    decoder.decode(ValueUtils.toByteArray((LzrArray) args[0]))
                    : decoder.decode(args[0].asString())
        );
    }

    private Base64.Decoder getDecoder(LzrValue[] args) {
        if (args.length == 2 && args[1].asInt() == 8) {
            return Base64.getUrlDecoder();
        }
        return Base64.getDecoder();
    }
}
