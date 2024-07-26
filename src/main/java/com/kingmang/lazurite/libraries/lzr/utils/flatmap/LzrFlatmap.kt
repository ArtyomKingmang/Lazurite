package com.kingmang.lazurite.libraries.lzr.utils.flatmap

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrValue
import com.kingmang.lazurite.utils.ValueUtils.consumeFunction

class LzrFlatmap : Function {
    override fun execute(vararg args: LzrValue): LzrValue {
        Arguments.check(2, args.size)
        if (args[0].type() != Types.ARRAY) {
            throw LzrException("TypeExeption ", "Array expected in first argument")
        }
        val mapper = consumeFunction(args[1], 1)
        return flatMapArray(args[0] as LzrArray, mapper)
    }

    private fun flatMapArray(array: LzrArray, mapper: Function): LzrValue {
        val values: MutableList<LzrValue> = ArrayList()
        val size = array.size()
        for (i in 0 until size) {
            val inner = mapper.execute(array[i])
            if (inner.type() != Types.ARRAY) {
                throw LzrException("TypeExeption ", "Array expected $inner")
            }
            for (value in inner as LzrArray) {
                values.add(value)
            }
        }
        return LzrArray(values)
    }
}