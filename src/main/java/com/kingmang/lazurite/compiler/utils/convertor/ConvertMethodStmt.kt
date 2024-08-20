package com.kingmang.lazurite.compiler.utils.convertor

import com.kingmang.lazurite.parser.ast.expressions.ValueExpression
import com.kingmang.lazurite.parser.ast.statements.*
import ru.DmN.pht.utils.node.nodeBody
import ru.DmN.pht.utils.node.nodeThrow
import ru.DmN.pht.utils.node.nodeValue
import com.kingmang.lazurite.compiler.utils.convertor.ConvertClassStmt.convertFunctionDefineStmt
import com.kingmang.lazurite.compiler.utils.convertor.ConvertFileStmt.convertClassDeclarationStmt
import com.kingmang.lazurite.compiler.utils.convertor.Convertor.convertMethod
import com.kingmang.lazurite.compiler.utils.node.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo

object ConvertMethodStmt {
    // Конвертация тела метода
    fun convertMethodStmt(stmt: Statement, info: INodeInfo, ctx: ConvertContext): Node =
        when (stmt) {
            is AssignmentStatement       -> convertAssigmentStmt(stmt, info, ctx)
            is BlockStatement            -> convertBlockStmt(stmt, info, ctx)
            is ClassDeclarationStatement -> convertClassDeclarationStmt(stmt, info, ctx)
            is ForStatement              -> convertForStmt(stmt, info, ctx)
            is FunctionDefineStatement   -> convertFunctionDefineStmt(stmt, info, ctx)
            is PrintlnStatement          -> convertPrintlnStmt(stmt, info, ctx)
            is PrintStatement            -> convertPrintStmt(stmt, info, ctx)
            is ReturnStatement           -> convertReturnStmt(stmt, info, ctx)
            is ThrowStatement            -> convertThrowStmt(stmt, info, ctx)
            is TryCatchStatement         -> convertTryCatchStmt(stmt, info, ctx)
            is UsingStatement            -> convertUsingStmt(stmt, info, ctx)
            else -> throw UnsupportedOperationException(stmt.javaClass.name)
        }

    fun convertAssigmentStmt(stmt: AssignmentStatement, info: INodeInfo, ctx: ConvertContext): Node =
        if (stmt.mode > 0)
            throw UnsupportedOperationException()
        else nodeDefSet(info, stmt.variable, convertMethod(stmt.expression, info, ctx))

    fun convertBlockStmt(stmt: BlockStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodeProgn(info, stmt.statements.mapMutable { convertMethod(it, info, ctx) })

    fun convertForStmt(stmt: ForStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodeBody(
            info,
            mutableListOf(
                convertMethod(stmt.initialization, info, ctx),
                nodeCycle(
                    info,
                    mutableListOf(
                        convertMethod(stmt.termination, info, ctx),
                        convertMethod(stmt.statement, info, ctx),
                        convertMethod(stmt.increment, info, ctx)
                    )
                )
            )
        )

    fun convertPrintlnStmt(stmt: PrintlnStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodePrintln(info, convertMethod(stmt.expression, info, ctx))

    fun convertPrintStmt(stmt: PrintStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodePrint(info, convertMethod(stmt.expression, info, ctx))

    fun convertReturnStmt(stmt: ReturnStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodeReturn(info, convertMethod(stmt.expression, info, ctx))

    fun convertThrowStmt(stmt: ThrowStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodeThrow(info, "LzrException", mutableListOf(nodeValue(info, stmt.type), convertMethod(stmt.expr, info, ctx)))

    fun convertTryCatchStmt(stmt: TryCatchStatement, info: INodeInfo, ctx: ConvertContext): Node =
        nodeCatch(info, convertMethod(stmt.tryStatement, info, ctx), convertMethod(stmt.catchStatement, info, ctx))

    fun convertUsingStmt(stmt: UsingStatement, info: INodeInfo, ctx: ConvertContext): Node =
        if (stmt.expression is ValueExpression)
            nodeUseLzr(info, (stmt.expression as ValueExpression).value.asString())
        else throw UnsupportedOperationException(stmt.expression.javaClass.name)
}