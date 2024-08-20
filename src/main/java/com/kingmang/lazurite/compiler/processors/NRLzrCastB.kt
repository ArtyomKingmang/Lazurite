package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.utils.node.*
import com.kingmang.lazurite.compiler.ast.NodeLzrCast
import com.kingmang.lazurite.compiler.utils.node.nodeLambda
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrCastB : INodeProcessor<NodeLzrCast>, IAdaptableProcessor<NodeLzrCast> {
    override fun calc(node: NodeLzrCast, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type

    override fun process(node: NodeLzrCast, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        node

    override fun adaptToType(node: NodeLzrCast, type: VirtualType, processor: Processor, ctx: ProcessingContext): Node {
        if (type.noStaticMethodsCount == 1) {
            val info = node.info
            val body = ArrayList<Node>()
            val instance = Variable.tmp(node)
            body += nodeDef(info, instance, node.value)
            body += nodeLambda(
                info,
                type.name,
                listOf(instance),
                emptyList(),
                nodeMCall(
                    info,
                    "LzrObjectUtils",
                    "invokeLambda",
                    mutableListOf<Node>(
                        nodeGetOrName(info, instance),
                        nodeArrayOfType(info, "Any", emptyList())
                    )
                )
            )
            return processor.process(nodeBody(info, body), ctx, true)!!
        }
        return node
    }

    override fun adaptableTo(node: NodeLzrCast, type: VirtualType, processor: Processor, ctx: ProcessingContext, ): Int =
        1

    private val VirtualType.noStaticMethodsCount: Int
        get() = this.methods.stream().filter { !it.modifiers.static }.count().toInt()
}