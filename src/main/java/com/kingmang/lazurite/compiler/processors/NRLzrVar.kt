package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.DEF_SET
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeValue
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRLzrVar : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(
            if (ctx.lzrRuntime) {
                val info = node.info
                nodeMCall(
                    info,
                    "LzrRuntimeUtils",
                    "defSetVariable",
                    mutableListOf(
                        nodeValue(info, processor.computeString(node.nodes[0], ctx)),
                        node.nodes[1]
                    )
                )
            } else NodeNodesList(node.info.withType(DEF_SET), node.nodes),
            ctx,
            valMode
        )
}