package com.kingmang.lazurite.libraries.lzr.utils.flatmap

import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.libraries.Library

class flatmap : Library {
    override fun init() {
        Keyword.put("flatmap", LzrFlatmap())
    }
}
