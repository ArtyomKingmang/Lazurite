package com.kingmang.lazurite.compiler.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.node.INodeInfo

class NodeLzrFSet(info: INodeInfo, val name: String, val instance: Node, val value: Node) : BaseNode(info)