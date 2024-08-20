package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrFGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VTDynamic

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        return processor.process(
            nodeMCall(
                info,
                "LzrObjectUtils",
                "getField",
                node.nodes,
            ),
            ctx,
            valMode
        )
    }
}