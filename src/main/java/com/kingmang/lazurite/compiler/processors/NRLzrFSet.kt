package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.ast.IValueNode
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.utils.isLiteral
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeValue
import com.kingmang.lazurite.compiler.ast.NodeLzrFSet
import com.kingmang.lazurite.compiler.utils.node.nodeFld
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRLzrFSet : INodeProcessor<NodeLzrFSet> {
    override fun process(node: NodeLzrFSet, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val instance = node.instance
        val method = ctx.method
        return processor.process(
            if (method.modifiers.ctor && instance.isLiteral && (instance as IValueNode).getValueAsString() == "this")
                nodeFld(info, node.name, node.value)
            else nodeMCall(
                info,
                "LzrObjectUtils",
                "setField",
                listOf(
                    node.instance,
                    nodeValue(info, node.name),
                    node.value
                )
            ),
            ctx,
            valMode
        )
    }
}