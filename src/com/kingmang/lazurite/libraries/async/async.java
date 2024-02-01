package com.kingmang.lazurite.libraries.async;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import java.util.concurrent.CompletableFuture;

public class async implements Library {

    @Override
    public void init(){
        LzrMap async = new LzrMap(1);
        async.set("supply", new Function() {
            @Override
            public Value execute(Value... args) {
                Arguments.check(1, args.length);
                CompletableFuture<LzrNumber> asyncc;
                asyncc = CompletableFuture.supplyAsync(() -> {
                    (((LzrFunction) args[0]).getValue()).execute();
                    return LzrNumber.ONE;
                });
                return LzrNumber.MINUS_ONE;
            }
        });
        Variables.define("async", async);
    }

}
