package com.kingmang.lazurite.compiler.utils.convertor

import com.kingmang.lazurite.parser.ast.statements.BlockStatement
import com.kingmang.lazurite.parser.ast.statements.ClassDeclarationStatement
import com.kingmang.lazurite.parser.ast.statements.Statement
import ru.DmN.pht.utils.node.nodeCcall
import com.kingmang.lazurite.compiler.utils.convertor.Convertor.convertFile
import com.kingmang.lazurite.compiler.utils.node.nodeClass
import com.kingmang.lazurite.compiler.utils.node.nodeLzrCtor
import com.kingmang.lazurite.compiler.utils.node.nodePrognB
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo

object ConvertFileStmt {
    // Конвератция тела файла
    fun convertFileStmt(stmt: Statement, info: INodeInfo, ctx: ConvertContext): Node =
        when (stmt) {
            is BlockStatement            -> convertBlockStmt(stmt, info, ctx)
            is ClassDeclarationStatement -> convertClassDeclarationStmt(stmt, info, ctx)
            else -> throw UnsupportedOperationException(stmt.javaClass.name)
        }

    fun convertClassDeclarationStmt(stmt: ClassDeclarationStatement, info: INodeInfo, ctx: ConvertContext): Node {
        val nodes = ArrayList<Node>()
        val ctor = ArrayList<Node>()
        ctor += nodeCcall(info)
        if (stmt.fields.isNotEmpty())
            throw UnsupportedOperationException() // todo: Fields define
        stmt.methods.forEach { nodes += ConvertClassStmt.convertFunctionDefineStmt(it, info, ctx) }
        nodes += nodeLzrCtor(info, ctor)
        return nodeClass(info, stmt.name, nodes)
    }

    fun convertBlockStmt(stmt: BlockStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodePrognB(info, stmt.statements.mapMutable { convertFile(it, info, ctx) })
}