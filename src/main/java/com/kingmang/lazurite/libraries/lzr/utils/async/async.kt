package com.kingmang.lazurite.libraries.lzr.utils.async

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrNumber
import java.util.concurrent.CompletableFuture

@Suppress("unused", "ClassName")
class async : Library {
    override fun init() {
        val async = LzrMap(1)
        async["supply"] = Function { args ->
            Arguments.check(1, args.size)
            val asyncc = CompletableFuture.supplyAsync {
                (args[0] as LzrFunction).value.execute()
                LzrNumber.ONE
            }
            LzrNumber.MINUS_ONE
        }
        Variables.define("async", async)
    }
}
