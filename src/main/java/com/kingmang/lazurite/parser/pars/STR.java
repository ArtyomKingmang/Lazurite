package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.runtime.LZR.LZRArray;

import com.kingmang.lazurite.runtime.Value;

import java.io.UnsupportedEncodingException;

public final class STR {

    private STR() { }

    static LZRArray getBytes(Value[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
        try {
            return LZRArray.of(args[0].asString().getBytes(charset));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }
    





}
