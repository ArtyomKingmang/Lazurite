package com.kingmang.lazurite.libraries.lzr.utils.arrays

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import com.kingmang.lazurite.utils.ValueUtils
import java.util.*
import kotlin.math.min

@Suppress("unused", "ClassName")
class arrays : Library {
    override fun init() {
        val array = LzrMap(4)
        array["join"] = Function { args ->
            Arguments.checkRange(1, 4, args.size)
            if (args[0].type() != Types.ARRAY) {
                throw LzrException("TypeExeption ", "Array expected in first argument")
            }

            val array = args[0] as LzrArray
            when (args.size) {
                1 -> LzrArray.joinToString(array)
                2 -> LzrArray.joinToString(array, args[1].asString())
                3 -> LzrArray.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString())
                4 -> LzrArray.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString())
                else -> throw LzrException("ArgumentsMismatchException ", "Wrong number of arguments")
            }
        }
        array["sort"] = Function { args ->
            Arguments.checkAtLeast(1, args.size)
            if (args[0].type() != Types.ARRAY) {
                throw LzrException("TypeExeption ", "Array expected in first argument")
            }
            val elements = (args[0] as LzrArray).copyElements

            when (args.size) {
                1 -> Arrays.sort(elements)
                2 -> {
                    val comparator = ValueUtils.consumeFunction(args[1], 1)
                    Arrays.sort(elements) { o1: LzrValue, o2: LzrValue ->
                        comparator.execute(o1, o2).asInt()
                    }
                }

                else -> throw LzrException("ArgumentsMismatchException ", "Wrong number of arguments")
            }
            LzrArray(elements)
        }
        array["combine"] = Function { args ->
            Arguments.check(2, args.size)
            if (args[0].type() != Types.ARRAY) {
                throw LzrException("TypeException", "Array expected in first argument")
            }
            if (args[1].type() != Types.ARRAY) {
                throw LzrException("TypeException", "Array expected in second argument")
            }

            val keys = (args[0] as LzrArray)
            val values = (args[1] as LzrArray)
            val length = min(keys.size().toDouble(), values.size().toDouble()).toInt()

            val result = LzrMap(length)
            for (i in 0 until length) {
                result[keys[i]] = values[i]
            }
            result
        }
        array["keyExists"] = Function { args ->
            Arguments.check(2, args.size)
            if (args[1].type() != Types.MAP) {
                throw LzrException("TypeException", "Map expected in second argument")
            }
            val map = (args[1] as LzrMap)
            LzrNumber.fromBoolean(map.containsKey(args[0]))
        }
        Variables.define("arrays", array)
    }
}
