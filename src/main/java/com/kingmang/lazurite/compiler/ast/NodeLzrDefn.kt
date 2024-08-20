package com.kingmang.lazurite.compiler.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo

class NodeLzrDefn(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val name: String,
    val args: List<String>
) : NodeNodesList(info, nodes)