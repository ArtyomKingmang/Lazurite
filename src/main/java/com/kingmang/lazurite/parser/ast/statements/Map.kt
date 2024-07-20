package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue
import com.kingmang.lazurite.utils.ValueUtils.consumeFunction

class Map : Function {
    override fun execute(vararg args: LzrValue): LzrValue {
        Arguments.checkOrOr(2, 3, args.size)

        val container = args[0]
        if (container.type() == Types.ARRAY) {
            val mapper = consumeFunction(args[1], 1)
            return mapArray(container as LzrArray, mapper)
        }

        if (container.type() == Types.MAP) {
            val keyMapper = consumeFunction(args[1], 1)
            val valueMapper = consumeFunction(args[2], 2)
            return mapMap(container as LzrMap, keyMapper, valueMapper)
        }

        throw LzrException("ArgumentsMismatchException ", "Invalid first argument. Array or map expected")
    }

    private fun mapArray(array: LzrArray, mapper: Function): LzrValue =
        LzrArray(array.size()) { index: Int? -> mapper.execute(array[index!!]) }

    private fun mapMap(map: LzrMap, keyMapper: Function, valueMapper: Function): LzrValue =
        LzrMap(map.size()).apply {
            for ((key, value) in map) {
                val newKey = keyMapper.execute(key)
                val newValue = valueMapper.execute(value)
                this[newKey] = newValue
            }
        }
}
