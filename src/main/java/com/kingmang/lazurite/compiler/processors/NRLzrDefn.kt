package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B
import com.kingmang.lazurite.compiler.ast.NodeLzrDefn
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import com.kingmang.lazurite.compiler.utils.node.NodeTypes.ANN_LZR_ENTRY
import com.kingmang.lazurite.compiler.utils.node.nodeGFn
import com.kingmang.lazurite.compiler.utils.node.nodeLzrVar
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod

object NRLzrDefn : INodeProcessor<NodeLzrDefn> {
    override fun process(node: NodeLzrDefn, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val nodes = ArrayList<Node>()
        if (ctx.clazz.name == node.name) {
            nodes += nodeCtor(
                info,
                node.args.map { Pair(it, "dynamic") },
                node.nodes.apply { add(0, nodeCcall(info)) }
            )
        } else {
            try {
                processor.process(
                    nodeDefn(
                        info,
                        node.name,
                        "dynamic",
                        node.args.map { Pair(it, "dynamic") },
                        node.nodes.let {
                            if ((it[0] as INodesList).nodes.isEmpty())
                                mutableListOf(nodeValueNil(info))
                            else it
                        }
                    ),
                    ctx,
                    false
                )?.let(nodes::add)
            } catch (_: Exception) {
            }
            nodes += nodeGFn(info, node.name, "auto", node.args.map { Pair(it, "auto") }, node.nodes)
        }
        return processor.process(
            NodeMetaNodesList(
                info.withType(
                    if (ctx.lzrRuntime)
                        ANN_LZR_ENTRY
                    else PROGN_B
                ),
                nodes
            ),
            ctx,
            valMode
        )
    }

    fun runtimeInject(node: INodesList, method: VirtualMethod, processor: Processor, ctx: ProcessingContext) {
        val info = node.info
        val nodes = node.nodes
        method.argsn.forEach { nodes.add(0, nodeLzrVar(info, it, nodeGetOrName(info, it))) }
        nodes.add(0, nodeMCall(info, "LzrRuntimeUtils", "pushVariables", emptyList()))
        nodes.add(nodeMCall(info, "LzrRuntimeUtils", "popVariables", emptyList()))
        nodes.add(
            1,
            nodeMCall(
                info,
                "LzrRuntimeUtils",
                "defSetVariable",
                mutableListOf(
                    nodeValue(info, "this"),
                    if (method.modifiers.static)
                        nodeValueClass(info, ctx.clazz.name)
                    else nodeGetOrName(info, "this")
                )
            )
        )
    }
}