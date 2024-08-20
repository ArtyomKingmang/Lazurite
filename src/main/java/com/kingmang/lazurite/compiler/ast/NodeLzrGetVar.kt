package com.kingmang.lazurite.compiler.ast

import ru.DmN.pht.ast.IValueNode
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo

class NodeLzrGetVar(info: INodeInfo, val name: String) : BaseNode(info), IValueNode {
    override fun isLiteral(): Boolean =
        true

    override fun getValueAsString(): String =
        this.name
}