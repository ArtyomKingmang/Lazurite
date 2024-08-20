package com.kingmang.lazurite.compiler.utils

import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.values.LzrValue
import com.kingmang.lazurite.compiler.utils.LzrObjectUtils.castToLzrValue

object LzrLibraryLoader {
    private val libraries: MutableMap<String, Library> = HashMap()

    @JvmStatic
    fun loadLibrary(name: String): Library =
        libraries.getOrPut(name) {
            Class.forName(
                "com.kingmang.lazurite.libraries.${name}.${
                    name.substring(
                        name.lastIndexOf(
                            '.'
                        ) + 1
                    )
                }"
            ).newInstance() as Library
        }

    @JvmStatic
    fun invokeMethod(library: String, method: String, args: Array<Any?>): LzrValue =
        invokeMethod(library, method, *castToLzrValue(args))

    @JvmStatic
    fun invokeMethod(library: String, method: String, vararg args: LzrValue): LzrValue =
        loadLibrary(library).provides()[method]!!.second.execute(*args)
}