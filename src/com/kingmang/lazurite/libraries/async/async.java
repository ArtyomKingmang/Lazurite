package com.kingmang.lazurite.libraries.async;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.core.ValueUtils;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class async implements Library {

    @Override
    public void init(){
        LZRMap async = new LZRMap(1);
        async.set("supply", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(1, args.length);
                CompletableFuture<LZRNumber> asyncc;
                asyncc = CompletableFuture.supplyAsync(() -> {
                    (((LZRFunction) args[0]).getValue()).execute();
                    return LZRNumber.ONE;
                });
                return LZRNumber.MINUS_ONE;
            }
        });
        Variables.define("async", async);
    }

}
