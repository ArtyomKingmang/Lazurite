package com.kingmang.lazurite.libraries.base64;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.core.ValueUtils;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.Value;

import java.nio.charset.StandardCharsets;
import java.sql.Types;
import java.util.Base64;

public class base64 implements Library {
    private static final int TYPE = 8;
    @Override
    public void init() {
        KEYWORD.put("BASE64Encode", this::encode);
    }
    private Value encode(Value... args){
        Arguments.checkOrOr(1,2,args.length);
        return LZRArray.of(enc(args).encode(input(args)));

    }

    private Base64.Encoder enc(Value[] args){
        if(args.length == 2 && args[1].asInt() == TYPE){
            return Base64.getUrlEncoder();
        }
        return Base64.getEncoder();
    }
    private byte[] input(Value[] args){
        byte[] input;
        if(args[0].type() == Types.ARRAY){
            input = ValueUtils.toByteArray((LZRArray) args[0]);
        }else{
            try{
                input = args[0].asString().getBytes(StandardCharsets.UTF_8);
            }catch(Exception e){
                input = args[0].asString().getBytes();
            }
        }
        return input;
    }
}
