package com.kingmang.lazurite.compiler.utils.convertor

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.ast.expressions.*
import com.kingmang.lazurite.parser.ast.statements.ExprStatement
import ru.DmN.pht.ast.NodeCompare
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.node.nodeNew
import ru.DmN.pht.utils.node.nodeOverSetLeft
import ru.DmN.pht.utils.node.nodeValue
import com.kingmang.lazurite.compiler.utils.convertor.Convertor.convertMethod
import com.kingmang.lazurite.compiler.utils.node.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo

object ConvertMethodExpr {
    // Конвертация тела метода
    fun convertMethodExpr(expr: Expression, info: INodeInfo, ctx: ConvertContext): Node =
        when (expr) {
            is AssignmentExpression         -> convertAssingmentExpr(expr, info, ctx)
            is BinaryExpression             -> convertBinaryExpr(expr, info, ctx)
            is ConditionalExpression        -> convertConditionalExpr(expr, info, ctx)
            is ContainerAccessExpression    -> convertContainerAccessExpr(expr, info, ctx)
            is ExprStatement                -> convertMethod(expr.expr, info, ctx)
            is FunctionalExpression         -> convertFunctionalExpr(expr, info, ctx)
            is FunctionReferenceExpression  -> convertFunctionReferenceExpr(expr, info, ctx)
            is ObjectCreationExpression     -> convertObjectCreationExpr(expr, info, ctx)
            is UnaryExpression              -> convertUnaryExpr(expr, info, ctx)
            is ValueExpression              -> convertValueExpr(expr, info, ctx)
            is VariableExpression           -> convertVariableExpr(expr, info, ctx)
            else -> throw UnsupportedOperationException(expr.javaClass.name)
        }

    fun convertAssingmentExpr(expr: AssignmentExpression, info: INodeInfo, ctx: ConvertContext): Node =
        if (expr.operation == null) {
            when (val target = expr.target) {
                is VariableExpression -> nodeLzrVar(info, target.name, convertMethod(expr.expression, info, ctx))
                is ContainerAccessExpression -> {
                    when (val it = target.indices[0]) {
                        is ValueExpression -> nodeLzrFSet(
                            info,
                            it.value.asString(),
                            convertMethod(target.root, info, ctx),
                            convertMethod(expr.expression, info, ctx)
                        )
                        is VariableExpression -> nodeLzrASet(
                            info,
                            convertMethod(target.root, info, ctx),
                            convertMethod(it, info, ctx),
                            convertMethod(expr.expression, info, ctx)
                        )
                        else -> throw UnsupportedOperationException(it.javaClass.name)
                    }
                }
                else -> throw UnsupportedOperationException(target.javaClass.name)
            }
        } else { // todo: remake to lzr_f(g/s)et
            nodeOverSetLeft(
                info,
                when (val target = expr.target) {
                    is VariableExpression -> convertMethod(target, info, ctx)
                    else -> throw UnsupportedOperationException(target.javaClass.name)
                },
                NodeNodesList(
                    info.withType(
                        when (expr.operation!!) {
                            BinaryExpression.Operator.ADD       -> ADD
                            BinaryExpression.Operator.SUBTRACT  -> SUB
                            BinaryExpression.Operator.MULTIPLY  -> MUL
                            BinaryExpression.Operator.DIVIDE    -> DIV
                            BinaryExpression.Operator.REMAINDER -> REM
                            else -> throw UnsupportedOperationException(expr.operation!!.name)
                        }
                    ),
                    mutableListOf(convertMethod(expr.expression, info, ctx))
                )
            )
        }


    fun convertBinaryExpr(expr: BinaryExpression, info: INodeInfo, ctx: ConvertContext): Node =
        NodeNodesList(
            info.withType(
                when (expr.operation) {
                    BinaryExpression.Operator.ADD       -> ADD
                    BinaryExpression.Operator.SUBTRACT  -> SUB
                    BinaryExpression.Operator.MULTIPLY  -> MUL
                    BinaryExpression.Operator.DIVIDE    -> DIV
                    BinaryExpression.Operator.REMAINDER -> REM
                    else -> throw UnsupportedOperationException(expr.operation.name)
                }
            ),
            mutableListOf(
                convertMethod(expr.expr1, info, ctx),
                convertMethod(expr.expr2, info, ctx)
            )
        )

    fun convertConditionalExpr(expr: ConditionalExpression, info: INodeInfo, ctx: ConvertContext): Node =
        NodeCompare(
            info.withType(
                when (expr.operation) {
                    ConditionalExpression.Operator.EQUALS     -> EQ
                    ConditionalExpression.Operator.NOT_EQUALS -> NOT_EQ
                    ConditionalExpression.Operator.LT         -> LESS
                    ConditionalExpression.Operator.LTEQ       -> LESS_OR_EQ
                    ConditionalExpression.Operator.GT         -> GREAT
                    ConditionalExpression.Operator.GTEQ       -> GREAT_OR_EQ
                    else -> throw UnsupportedOperationException(expr.operation.name)
                }
            ),
            mutableListOf(
                convertMethod(expr.expr1, info, ctx),
                convertMethod(expr.expr2, info, ctx)
            )
        )

    fun convertContainerAccessExpr(expr: ContainerAccessExpression, info: INodeInfo, ctx: ConvertContext): Node =
        when (val index = expr.indices[0]) {
            is ValueExpression -> {
                val value = index.value
                when (value.type()) {
                    Types.NUMBER -> nodeLzrAGet(info, convertMethod(expr.root, info, ctx), convertMethod(index, info, ctx))
                    Types.STRING -> nodeLzrFGet(info, value.asString(), convertMethod(expr.root, info, ctx))
                    else -> throw UnsupportedOperationException("(type = ${value.type()} | value = ${value.raw()})")
                }
            }

            is VariableExpression -> nodeLzrAGet(info, convertMethod(expr.root, info, ctx), convertMethod(index, info, ctx))
            else -> throw UnsupportedOperationException(index.javaClass.name)
        }

    fun convertFunctionalExpr(expr: FunctionalExpression, info: INodeInfo, ctx: ConvertContext): Node =
        when (val func = expr.functionExpr) {
            is ContainerAccessExpression ->
                nodeLzrICall(
                    info,
                    (func.indices[0] as ValueExpression).value.asString(),
                    convertMethod(func.root, info, ctx),
                    expr.arguments.mapMutable { convertMethod(it, info, ctx) }
                )
            is VariableExpression ->
                nodeLzrCall(
                    info,
                    func.name,
                    expr.arguments.mapMutable { convertMethod(it, info, ctx) }
                )
            is ValueExpression ->
                nodeLzrCall(
                    info,
                    func.value.asString(),
                    expr.arguments.mapMutable { convertMethod(it, info, ctx) }
                )
            else -> throw UnsupportedOperationException(func.javaClass.name)
        }

    fun convertFunctionReferenceExpr(expr: FunctionReferenceExpression, info: INodeInfo, ctx: ConvertContext): Node =
        nodeLzrGetVar(info, expr.name)

    fun convertObjectCreationExpr(expr: ObjectCreationExpression, info: INodeInfo, ctx: ConvertContext): Node =
        nodeNew(info, expr.className, expr.constructorArguments.map { convertMethod(it, info, ctx) })

    fun convertUnaryExpr(expr: UnaryExpression, info: INodeInfo, ctx: ConvertContext): Node =
        NodeNodesList(
            info.withType(
                when (expr.operation) {
                    UnaryExpression.Operator.DECREMENT_PREFIX  -> DEC_PRE
                    UnaryExpression.Operator.DECREMENT_POSTFIX -> DEC_POST
                    UnaryExpression.Operator.INCREMENT_PREFIX  -> INC_PRE
                    UnaryExpression.Operator.INCREMENT_POSTFIX -> INC_POST
                    else -> throw UnsupportedOperationException(expr.operation.name)
                }
            ), mutableListOf(convertMethod(expr.expr1, info, ctx)))

    fun convertValueExpr(expr: ValueExpression, info: INodeInfo, ctx: ConvertContext): Node {
        val value = expr.value
        return when (value.type()) {
            Types.NUMBER -> nodeValue(info, value.asNumber())
            Types.STRING -> nodeValue(info, value.asString())
            else -> throw UnsupportedOperationException("(type = ${value.type()} | value = ${value.raw()})")
        }
    }

    fun convertVariableExpr(expr: VariableExpression, info: INodeInfo, ctx: ConvertContext): Node =
        nodeLzrGetVar(info, expr.name)
}