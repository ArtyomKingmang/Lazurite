package com.kingmang.lazurite.optimization

import com.kingmang.lazurite.runtime.values.LzrValue


class VariableInfo {
    @JvmField
    var value: LzrValue? = null
    @JvmField
    var modifications: Int = 0

    override fun toString(): String {
        return (if (value == null) "?" else value).toString() + " (" + modifications + " mods)"
    }
}