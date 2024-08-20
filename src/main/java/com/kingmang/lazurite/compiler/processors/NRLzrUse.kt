package com.kingmang.lazurite.compiler.processors

import com.kingmang.lazurite.core.Function
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeValue
import com.kingmang.lazurite.compiler.processor.ctx.lzrFunctions
import com.kingmang.lazurite.compiler.processor.ctx.lzrFunctionsOrNull
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import com.kingmang.lazurite.compiler.utils.LzrLibraryLoader
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRLzrUse : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val name = processor.computeString(node.nodes[0], ctx)
        if (ctx.lzrRuntime) {
            val info = node.info
            return processor.process(
                nodeMCall(
                    info,
                    "LzrRuntimeUtils",
                    "loadLibrary",
                    mutableListOf(nodeValue(info, name))
                ),
                ctx,
                valMode
            )
        } else {
            if (name.endsWith(".lzr"))
                return NRIncLzr.process(node.info, name, null, processor, ctx, valMode)
            val library = LzrLibraryLoader.loadLibrary(name)
            val provides = library.provides()
            if (provides.isEmpty())
                return null
            var functions = ctx.lzrFunctionsOrNull
            if (functions == null)
                functions =
                    ArrayList<Pair<String, MutableMap<String, Pair<Int, Function>>>>().apply { ctx.lzrFunctions = this }
            functions.add(Pair(name, provides))
            return node
        }
    }
}