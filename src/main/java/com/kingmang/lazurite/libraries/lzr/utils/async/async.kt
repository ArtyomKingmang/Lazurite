package com.kingmang.lazurite.libraries.lzr.utils.async

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrNumber
import java.util.concurrent.CompletableFuture

@Suppress("unused", "ClassName")
class async : Library {
    override fun init() {
        Variables.define("async", async())
    }

    override fun provides(): MutableMap<String, Pair<Int, Function>> {
        val map: MutableMap<String, Pair<Int, Function>> = HashMap()
        map["async"] = Pair(Types.MAP, Function { async() })
        return map
    }

    private fun async(): LzrMap {
        val async = LzrMap(1)
        async["supply"] = Function { args ->
            Arguments.check(1, args.size)
            CompletableFuture.supplyAsync {
                (args[0] as LzrFunction).value.execute()
                LzrNumber.ONE
            }
            LzrNumber.MINUS_ONE
        }
        return async
    }
}
