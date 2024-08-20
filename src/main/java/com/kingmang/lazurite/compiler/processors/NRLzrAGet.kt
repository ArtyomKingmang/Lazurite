package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRLzrAGet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        return processor.process(
            nodeMCall(
                info,
                "LzrObjectUtils",
                "arrayGet",
                node.nodes
            ),
            ctx,
            valMode
        )
    }
}