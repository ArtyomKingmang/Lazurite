package com.kingmang.lazurite.compiler.utils.node

import ru.DmN.pht.utils.node.IParsedNodeType
import ru.DmN.siberia.utils.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    ;

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}