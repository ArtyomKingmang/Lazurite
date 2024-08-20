package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.processors.IComputableProcessor
import ru.DmN.pht.processors.ISetterGetterProcessor
import ru.DmN.pht.processors.NRGetB
import ru.DmN.pht.processors.NRGetOrName
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeNewArray
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.vtype.VTDynamic
import com.kingmang.lazurite.compiler.ast.NodeLzrGetVar
import com.kingmang.lazurite.compiler.processor.ctx.lzrFunctionsOrNull
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import com.kingmang.lazurite.compiler.utils.convertor.TypeConvertor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRLzrGetVar : IComputableProcessor<NodeLzrGetVar>, ISetterGetterProcessor<NodeLzrGetVar> {
    override fun calc(node: NodeLzrGetVar, processor: Processor, ctx: ProcessingContext): VirtualType? {
        if (ctx.lzrRuntime)
            return VTDynamic
        val name = node.name
        ctx.lzrFunctionsOrNull?.let {
            for (pair in it) {
                val function = pair.second[name] ?: continue
                return TypeConvertor.convert(function.first, processor, ctx)
            }
        }
        return NRGetOrName.calc(node.info, node.name, processor, ctx)
    }

    override fun process(node: NodeLzrGetVar, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        if (!valMode)
            return null
        val name = node.name
        ctx.lzrFunctionsOrNull?.let {
            for (pair in it) {
                pair.second[name] ?: continue
                val info = node.info
                return processor.process(
                    nodeMCall(
                        info,
                        "ru.DmN.phtx.pls.utils.LibraryLoader",
                        "invokeMethod",
                        mutableListOf<Node>(
                            nodeValue(info, pair.first),
                            nodeValue(info, name),
                            nodeNewArray(info, "Any", 0)
                        )
                    ),
                    ctx,
                    true
                )
            }
        }
        return if (ctx.lzrRuntime) {
            val info = node.info
            processor.process(
                nodeMCall(
                    info,
                    "LzrRuntimeUtils",
                    "getVariable",
                    mutableListOf(nodeValue(info, name))
                ),
                ctx,
                true
            )
        } else NRGetB.process(node.info, node.name, mutableListOf(), processor, ctx, true)
    }

    override fun computeString(node: NodeLzrGetVar, processor: Processor, ctx: ProcessingContext): String =
        node.name

    override fun processAsSetter(node: NodeLzrGetVar, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(processAsSetterLazy(node, values, processor, ctx, valMode), ctx, valMode)

    override fun processAsSetterLazy(node: NodeLzrGetVar, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        return nodeMCall(
            info,
            "LzrRuntimeUtils",
            "defSetVariable",
            mutableListOf<Node>(nodeValue(info, node.name)).apply { this += values }
        )
    }


    override fun processAsGetter(node: NodeLzrGetVar, processor: Processor, ctx: ProcessingContext): Node? =
        process(node, processor, ctx, true)
}