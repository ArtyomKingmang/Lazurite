package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.dropMutable
import ru.DmN.pht.utils.node.nodeIf
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrICall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VTDynamic

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val instance = node.nodes[0]
        val name = processor.computeString(node.nodes[1], ctx)
        val args = node.nodes.dropMutable(2)
        return processor.process(
            nodeProgn(
                info,
                mutableListOf(
                    nodeIf(
                        info,
                        mutableListOf(
                            nodeValue(info, false),
                            nodeMCall(info, instance, name, args)
                        )
                    ),
                    nodeMCall(
                        info,
                        "LzrObjectUtils",
                        "invokeMethod",
                        mutableListOf(
                            instance,
                            nodeValue(info, name),
                        ).apply { this += args }
                    )
                )
            ),
            ctx,
            valMode
        )
    }
}