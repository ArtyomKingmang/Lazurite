package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.utils.node.NodeParsedTypes.CTOR
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRLzrCtor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val type = ctx.clazz
        return if (type.methods.any { it.modifiers.ctor && it.argsc.isEmpty() })
            null
        else processor.process(NodeNodesList(node.info.withType(CTOR), node.nodes), ctx, valMode)
    }
}