package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.dropMutable
import ru.DmN.pht.utils.node.nodeArrayOfType
import ru.DmN.pht.utils.node.nodeGetOrName
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.vtype.VTDynamic
import com.kingmang.lazurite.compiler.processor.ctx.lzrFunctionsOrNull
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import com.kingmang.lazurite.compiler.utils.convertor.TypeConvertor
import com.kingmang.lazurite.compiler.utils.mapMutable
import com.kingmang.lazurite.compiler.utils.node.nodeLzrCast
import com.kingmang.lazurite.compiler.utils.node.nodeLzrGetVar
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val info = node.info
        val name = processor.computeString(node.nodes[0], ctx)
        //
        ctx.lzrFunctionsOrNull?.let {
            for (pair in it) {
                val function = pair.second[name] ?: continue
                return TypeConvertor.convert(function.first, processor, ctx)
            }
        }
        //
        return try {
            processor.calc(nodeMCall(info, nodeGetOrName(info, "."), name, node.nodes.dropMutable(1)), ctx)
        } catch (e: Exception) {
            VTDynamic
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val name = processor.computeString(node.nodes[0], ctx)
        //
        ctx.lzrFunctionsOrNull?.let { it ->
            for (pair in it) {
                pair.second[name] ?: continue
                return processor.process(
                    nodeMCall(
                        info,
                        "LzrLibraryLoader",
                        "invokeMethod",
                        mutableListOf<Node>(
                            nodeValue(info, pair.first),
                            nodeValue(info, name),
                            nodeArrayOfType(info, "Any", node.nodes.mapMutable(1) { processor.process(it, ctx, true)!! })
                        )
                    ),
                    ctx,
                    true
                )
            }
        }
        //
        val args = node.nodes.mapMutable(1) { nodeLzrCast(info, it, "dynamic") }
        return try {
            processor.process(nodeMCall(info, nodeGetOrName(info, "."), name, args), ctx, valMode)
        } catch (e: Exception) {
//            e.printStackTrace()
            processor.process(
                if (ctx.lzrRuntime)
                    nodeMCall(
                        info,
                        "LzrObjectUtils",
                        "invokeLambdaRt",
                        mutableListOf<Node>(
                            nodeLzrGetVar(info, name),
                            nodeValue(info, name),
                            nodeArrayOfType(info, "Any", args)
                        )
                    )
                else nodeMCall(
                    info,
                    "LzrObjectUtils",
                    "invokeLambda",
                    mutableListOf<Node>(
                        nodeLzrGetVar(info, name),
                        nodeArrayOfType(info, "Any", args)
                    )
                ),
                ctx,
                true
            )
        }
    }
}