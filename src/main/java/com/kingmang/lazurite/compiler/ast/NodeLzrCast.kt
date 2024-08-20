package com.kingmang.lazurite.compiler.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeLzrCast(
    info: INodeInfo,
    var value: Node,
    val type: VirtualType
) : BaseNode(info)