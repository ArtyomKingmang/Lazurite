package com.kingmang.lazurite.patterns.visitor

import com.kingmang.lazurite.parser.ast.expressions.*
import com.kingmang.lazurite.parser.ast.statements.*

interface ResultVisitor<R, T> {
    fun visit(s : DoWhileStatement, t : T): R
    fun visit(s: AssertStatement, t: T): R
    fun visit(s: TryCatchStatement, t: T): R
    fun visit(s: ArrayExpression, t: T): R
    fun visit(s: AssignmentExpression, t: T): R
    fun visit(s: BinaryExpression, t: T): R
    fun visit(s: BlockStatement, t: T): R
    fun visit(s: BreakStatement, t: T): R
    fun visit(s: ClassDeclarationStatement, t: T): R
    fun visit(s: ConditionalExpression, t: T): R
    fun visit(s: ContainerAccessExpression, t: T): R
    fun visit(s: ContinueStatement, t: T): R
    fun visit(s: DestructuringAssignmentStatement, t: T): R
    fun visit(s: ForStatement, t: T): R
    fun visit(s: ForeachArrayStatement, t: T): R
    fun visit(s: ForeachMapStatement, t: T): R
    fun visit(s: FunctionDefineStatement, t: T): R
    fun visit(s: FunctionReferenceExpression, t: T): R
    fun visit(s: ExprStatement, t: T): R
    fun visit(s: FunctionalExpression, t: T): R
    fun visit(s: IfStatement, t: T): R
    fun visit(s: MapExpression, t: T): R
    fun visit(s: MatchExpression, t: T): R
    fun visit(s: ObjectCreationExpression, t: T): R
    fun visit(s: PrintStatement, t: T): R
    fun visit(s: PrintlnStatement, t: T): R
    fun visit(s: ReturnStatement, t: T): R
    fun visit(s: TernaryExpression, t: T): R
    fun visit(s: UnaryExpression, t: T): R
    fun visit(s: ValueExpression, t: T): R
    fun visit(s: VariableExpression, t: T): R
    fun visit(s: WhileStatement, t: T): R
    fun visit(s: UsingStatement, t: T): R
}