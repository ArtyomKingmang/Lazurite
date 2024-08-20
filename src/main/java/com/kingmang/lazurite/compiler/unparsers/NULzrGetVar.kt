package com.kingmang.lazurite.compiler.unparsers

import com.kingmang.lazurite.compiler.ast.NodeLzrGetVar
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NULzrGetVar : INodeUnparser<NodeLzrGetVar> {
    override fun unparse(node: NodeLzrGetVar, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(' ').append(node.name).append(')')
    }
}