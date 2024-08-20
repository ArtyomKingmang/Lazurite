package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processor.ctx.cast
import ru.DmN.pht.processor.utils.adaptToType
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.processors.IAdaptableProcessor
import com.kingmang.lazurite.compiler.ast.NodeLzrCast
import com.kingmang.lazurite.compiler.utils.node.NodeTypes.LZR_CAST_
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.FINALIZATION
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrCast : INodeProcessor<NodeNodesList>, IAdaptableProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        NodeLzrCast(
            node.info.withType(LZR_CAST_),
            processor.process(node.nodes[1], ctx, true)!!,
            calc(node, processor, ctx)!!
        ).apply { finalize(this, processor, ctx) }

    private fun finalize(node: NodeLzrCast, processor: Processor, ctx: ProcessingContext) {
        processor.pushOrRunTask(FINALIZATION, node) {
            node.value = ctx.cast(node.type, node.value, processor, ctx)
        }
    }

    override fun adaptToType(node: NodeNodesList, type: VirtualType, processor: Processor, ctx: ProcessingContext): Node =
        processor.adaptToType(process(node, processor, ctx, true), type, ctx)

    override fun adaptableTo(node: NodeNodesList, type: VirtualType, processor: Processor, ctx: ProcessingContext, ): Int =
        1
}