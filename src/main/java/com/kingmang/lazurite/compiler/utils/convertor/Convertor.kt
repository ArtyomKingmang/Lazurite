package com.kingmang.lazurite.compiler.utils.convertor

import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.parser.ast.statements.Statement
import ru.DmN.pht.processor.ctx.isClass
import ru.DmN.pht.processor.ctx.isMethod
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.node.INodeInfo
import com.kingmang.lazurite.parser.ast.Node as LzrNode

object Convertor {
    // Конвертация первого stmt
    fun convert(stmt: Statement, info: INodeInfo, ctx: ConvertContext): Node =
        if (ctx.processingContext.isClass())
            if (ctx.processingContext.isMethod())
                convertMethod(stmt, info, ctx) // Method Parse
            else convertClass(stmt, info, ctx) // Class Parse
        else convertFile(stmt, info, ctx) // File Parse

    // Конвертация тела метода (Node)
    fun convertMethod(node: LzrNode, info: INodeInfo, ctx: ConvertContext): Node =
        when (node) {
            is Expression -> ConvertMethodExpr.convertMethodExpr(node, info, ctx)
            is Statement  -> ConvertMethodStmt.convertMethodStmt(node, info, ctx)
            else -> throw UnsupportedOperationException(node.javaClass.name)
        }

    // Конвертация тела класса (Node)
    fun convertClass(node: LzrNode, info: INodeInfo, ctx: ConvertContext): Node =
        when (node) {
            is Statement -> ConvertClassStmt.convertClassStmt(node, info, ctx)
            else -> throw UnsupportedOperationException(node.javaClass.name)
        }

    // Конвертация тела класса (Node)
    fun convertFile(node: LzrNode, info: INodeInfo, ctx: ConvertContext): Node =
        when (node) {
            is Statement -> ConvertFileStmt.convertFileStmt(node, info, ctx)
            else -> throw UnsupportedOperationException(node.javaClass.name)
        }
}