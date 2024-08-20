package com.kingmang.lazurite.compiler.utils.node

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.runtime.values.LzrValue
import ru.DmN.pht.ast.NodeFMGet
import ru.DmN.pht.ast.NodeFieldA
import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.DOUBLE
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.node.nodeGetOrName
import ru.DmN.pht.utils.node.nodeValn
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.node.nodeValueClass
import com.kingmang.lazurite.compiler.ast.NodeLzrDefn
import com.kingmang.lazurite.compiler.ast.NodeLzrFSet
import com.kingmang.lazurite.compiler.ast.NodeLzrGetVar
import com.kingmang.lazurite.compiler.utils.node.NodeTypes.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo

// a
fun nodeArrayOf(info: INodeInfo, elements: MutableList<Node>) =
    NodeNodesList(info.withType(ARR_OF), elements)
fun nodeAs(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(AS), nodes)
fun nodeAs(info: INodeInfo, type: String, value: Node) =
    NodeNodesList(info.withType(AS), mutableListOf(nodeValueClass(info, type), value))
// c
fun nodeCatch(info: INodeInfo, body: Node, catch: Node) =
    NodeNodesList(info.withType(CATCH),
        mutableListOf(
            nodeValn(
                info,
                nodeValn(
                    info,
                    mutableListOf(
                        nodeGetOrName(info, "exception"),
                        nodeValueClass(info, "com.kingmang.lazurite.exceptions.LzrException"),
                        catch))
            ), body))
fun nodeClass(info: INodeInfo, name: String, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(CLS),
        nodes.apply {
            add(0, nodeValue(info, name))
            add(1, nodeValn(info, nodeValueClass(info, "LzrMetaObject"))) })
fun nodeCycle(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(CYCLE), nodes)
// d
fun nodeDefSet(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(DEF_SET), mutableListOf(nodeGetOrName(info, name), value))
// f
fun nodeFld(info: INodeInfo, name: String, value: Node) =
    NodeFieldA(info.withType(FLD), mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
fun nodeFGet(info: INodeInfo, name: String, instance: Node, static: Boolean) =
    NodeFMGet(info.withType(FGET_B), mutableListOf(), instance, name, static)
fun nodeFSet(info: INodeInfo, name: String, instance: Node, value: Node) =
    NodeNodesList(info.withType(FSET_A), mutableListOf(instance, nodeGetOrName(info, name), value))
// g
fun nodeGFn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(GFN),
        mutableListOf<Node>(
            nodeGetOrName(info, name),
            nodeValueClass(info, ret),
            nodeValn(info, args.mapMutable { nodeValn(info, mutableListOf(nodeGetOrName(info, it.first), nodeValueClass(info, it.second))) })
        ).apply { addAll(nodes) })
// l
fun nodeLambda(info: INodeInfo, type: String, vars: List<String>, args: List<String>, body: Node) =
    NodeNodesList(info.withType(FN),
        mutableListOf<Node>(
            nodeValueClass(info, type),
            nodeValn(info, vars.mapMutable { nodeGetOrName(info, it) }),
            nodeValn(info, args.mapMutable { nodeGetOrName(info, it) })
        ).apply { add(body) })
fun nodeLzrAGet(info: INodeInfo, node: Node, index: Node) =
    NodeNodesList(info.withType(LZR_AGET), mutableListOf(node, index))
fun nodeLzrASet(info: INodeInfo, node: Node, index: Node, value: Node) =
    NodeNodesList(info.withType(LZR_ASET), mutableListOf(node, index, value))
fun nodeLzrCall(info: INodeInfo, name: String, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(LZR_CALL), nodes.apply { add(0, nodeGetOrName(info, name)) })
fun nodeLzrICall(info: INodeInfo, name: String, instance: Node, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(LZR_ICALL), nodes.apply { add(0, instance); add(1, nodeValue(info, name)) })
fun nodeLzrCast(info: INodeInfo, value: Node, type: String) =
    NodeNodesList(info.withType(LZR_CAST), mutableListOf(nodeValueClass(info, type), value))
fun nodeLzrCtor(info: INodeInfo, body: MutableList<Node>) =
    NodeNodesList(info.withType(LZR_CTOR), body.apply { add(0, nodeValn(info, mutableListOf())) })
fun nodeLzrDefn(info: INodeInfo, name: String, args: List<String>, nodes: MutableList<Node>) =
    NodeLzrDefn(info.withType(LZR_DEFN), nodes, name, args)
fun nodeLzrFSet(info: INodeInfo, name: String, instance: Node, value: Node) =
    NodeLzrFSet(info.withType(LZR_FSET), name, instance, value)
fun nodeLzrFGet(info: INodeInfo, name: String, instance: Node) =
    NodeNodesList(info.withType(LZR_FGET), mutableListOf(instance, nodeValue(info, name)))
fun nodeLzrGetVar(info: INodeInfo, name: String) =
    NodeLzrGetVar(info.withType(LZR_GET_VAR), name)
fun nodeLzrVar(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(LZR_VAR), mutableListOf(nodeGetOrName(info, name), value))
// p
fun nodePrognB(info: INodeInfo, nodes: MutableList<Node>) =
    NodeMetaNodesList(info.withType(PROGN_B), nodes)
fun nodePrintln(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(PRINTLN), mutableListOf(node))
fun nodePrint(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(PRINT), mutableListOf(node))
// r
fun nodeReturn(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(RET), mutableListOf(node))
// u
fun nodeUseLzr(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(LZR_USE), mutableListOf(nodeValue(info, name)))
// v
fun nodeValue(info: INodeInfo, value: Double) =
    NodeValue(info.withType(VALUE), DOUBLE, value.toString())
fun nodeValue(info: INodeInfo, value: LzrValue) =
    when (value.type()) {
        Types.NUMBER -> nodeValue(info, value.asNumber())
        Types.STRING -> nodeValue(info, value.asString())
        else -> throw UnsupportedOperationException()
    }
// @
fun nodeAnnLzrEntry(info: INodeInfo, nodes: MutableList<Node>) =
    NodeMetaNodesList(info.withType(ANN_LZR_ENTRY), nodes)