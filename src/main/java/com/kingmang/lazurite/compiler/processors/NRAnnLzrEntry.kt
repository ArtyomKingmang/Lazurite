package com.kingmang.lazurite.compiler.processors

import ru.DmN.pht.ast.IVMProviderNode
import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processors.NRAnnToEfn.forEachAllMetaNodesListA
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B_
import ru.DmN.pht.utils.node.nodeDefSet
import ru.DmN.pht.utils.node.nodeGetOrName
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable

object NRAnnLzrEntry : INodeProcessor<NodeMetaNodesList> {
    override fun process(node: NodeMetaNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        val nodes = ArrayList<Node>()
        val methods = ArrayList<IVMProviderNode<*>>()
        forEachAllMetaNodesListA(
            node,
            nodes,
            processor,
            ctx.with { value, _, _ ->
                nodeProgn(
                    info,
                    mutableListOf<Node>(nodeMCall(info, "LzrRuntimeUtils", "popVariables", emptyList()))
                        .apply {
                            value?.let {
                                val tmp = Variable.tmp(node)
                                this.add(0, nodeDefSet(info, tmp, it))
                                this.add(nodeGetOrName(info, tmp))
                            }
                        }
                )
            },
            valMode
        ) {
            if (it is IVMProviderNode<*> && it is INodesList) {
                methods += it
                false
            } else true
        }
        methods.forEach { NRLzrDefn.runtimeInject(it as INodesList, it.method, processor, ctx) }
        return NodeMetaNodesList(info.withType(PROGN_B_), node.metadata, nodes)
    }
}