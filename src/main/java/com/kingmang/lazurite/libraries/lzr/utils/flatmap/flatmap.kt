package com.kingmang.lazurite.libraries.lzr.utils.flatmap

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.libraries.Library

class flatmap : Library {
    override fun init() {
        Keyword.put("flatmap", LzrFlatmap())
    }

    override fun provides(): MutableMap<String, Pair<Int, Function>> {
        val map: MutableMap<String, Pair<Int, Function>> = HashMap()
        map["flatmap"] = Pair(Types.ARRAY, LzrFlatmap())
        return map
    }
}
