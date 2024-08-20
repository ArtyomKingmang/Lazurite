package com.kingmang.lazurite.compiler.parsers

import ru.DmN.pht.ast.NodeIncPht
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.node.INodeType

class NPIncLzr(val type: INodeType) : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeIncPht(INodeInfo.of(type, ctx, token), it, ctx) }
}