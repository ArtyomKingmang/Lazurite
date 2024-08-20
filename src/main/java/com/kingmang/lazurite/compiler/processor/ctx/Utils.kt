@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
package com.kingmang.lazurite.compiler.processor.ctx

import com.kingmang.lazurite.core.Function
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.processor.utils.LinkedClassesNode.LinkedClassesNodeStart
import com.kingmang.lazurite.compiler.utils.ctx.ContextKeys.*
import ru.DmN.siberia.utils.ctx.IContextCollection

inline fun <T : IContextCollection<T>> T.withLzrUsing(file: String): T =
    this.with(LZR_USING, LinkedClassesNode(this.lzrUsingOrNull ?: LinkedClassesNodeStart as LinkedClassesNode<String>, file))

inline var <T : IContextCollection<T>> T.lzrRuntime: Boolean
    set(value) { this.contexts[LZR_RUNTIME] = value }
    get() = contexts[LZR_RUNTIME] == true
inline val <T : IContextCollection<T>> T.lzrUsingOrNull: LinkedClassesNode<String>?
    get() = contexts[LZR_USING] as LinkedClassesNode<String>?
inline var <T : IContextCollection<T>> T.lzrFunctions: MutableList<Pair<String, MutableMap<String, Pair<Int, Function>>>>
    set(value) { contexts[LZR_FUNCTIONS] = value }
    get() = contexts[LZR_FUNCTIONS] as MutableList<Pair<String, MutableMap<String, Pair<Int, Function>>>>
inline val <T : IContextCollection<T>> T.lzrFunctionsOrNull: MutableList<Pair<String, MutableMap<String, Pair<Int, Function>>>>?
    get() = contexts[LZR_FUNCTIONS] as MutableList<Pair<String, MutableMap<String, Pair<Int, Function>>>>?