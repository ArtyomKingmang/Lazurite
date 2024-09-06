package com.kingmang.lazurite.libraries.lzrx.awt.colors

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString


@Suppress("unused", "ClassName")
class colors : Library {

    override fun init() {
        val col = LzrMap(10)
        col["reset"] = LzrString("\u001b[10m")
        col["red"] = LzrString("\u001b[31m")
        col["green"] = LzrString("\u001b[32m")
        col["blue"] = LzrString("\u001b[34m")
        col["white"] = LzrString("\u001b[37m")
        col["black"] = LzrString("\u001b[40m")
        col["purple"] = LzrString("\u001b[35m")
        col["yellow"] = LzrString("\u001b[33m")
        col["cyan"] = LzrString("\u001b[36m")
        col["clear"] = Function {
            print("\u001b[H\u001b[2J")
            System.out.flush()
            LzrNumber.ZERO
        }
        define("color", col)
    }

}