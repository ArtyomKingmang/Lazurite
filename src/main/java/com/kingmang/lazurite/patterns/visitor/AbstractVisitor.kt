package com.kingmang.lazurite.patterns.visitor

import com.kingmang.lazurite.parser.ast.expressions.*
import com.kingmang.lazurite.parser.ast.statements.*

abstract class AbstractVisitor : Visitor {
    override fun visit(s: ArrayExpression) {
        s.elements.forEach { it.accept(this) }
    }

    override fun visit(s: AssignmentExpression) {
        s.expression.accept(this)
    }

    override fun visit(s: BinaryExpression) {
        s.expr1.accept(this)
        s.expr2.accept(this)
    }

    override fun visit(s: BlockStatement) {
        s.statements.forEach { it.accept(this) }
    }

    override fun visit(s : DoWhileStatement) {
        s.condition.accept(this);
        s.statement.accept(this);
    }
    override fun visit(s: BreakStatement) = Unit
    override fun visit(s: AssertStatement) = Unit
    override fun visit(s: ClassDeclarationStatement) = Unit

    override fun visit(s: ConditionalExpression) {
        s.expr1.accept(this)
        s.expr2.accept(this)
    }

    override fun visit(s: ContainerAccessExpression) {
        s.root.accept(this)
        s.indices.forEach { it.accept(this) }
    }

    override fun visit(s: TryCatchStatement) = Unit
    override fun visit(s: ContinueStatement) = Unit

    override fun visit(s: DestructuringAssignmentStatement) {
        s.containerExpression.accept(this)
    }


    override fun visit(s: ForStatement) {
        s.initialization.accept(this)
        s.termination.accept(this)
        s.increment.accept(this)
        s.statement.accept(this)
    }

    override fun visit(s: ForeachArrayStatement) {
        s.container.accept(this)
        s.body.accept(this)
    }

    override fun visit(s: ForeachMapStatement) {
        s.container.accept(this)
        s.body.accept(this)
    }

    override fun visit(s: FunctionDefineStatement) {
        s.body.accept(this)
    }

    override fun visit(e: FunctionReferenceExpression) = Unit

    override fun visit(s: ExprStatement) {
        s.expr.accept(this)
    }

    override fun visit(s: FunctionalExpression) {
        s.functionExpr.accept(this)
        s.arguments.forEach { it.accept(this) }
    }

    override fun visit(s: IfStatement) {
        s.expression.accept(this)
        s.ifStatement.accept(this)
        s.elseStatement?.accept(this)
    }


    override fun visit(s: MapExpression) {
        for ((key, value) in s.elements) {
            key.accept(this)
            value.accept(this)
        }
    }

    override fun visit(s: MatchExpression) {
        s.expression.accept(this)
    }

    override fun visit(s: ObjectCreationExpression) {
        s.constructorArguments.forEach { it.accept(this) }
    }

    override fun visit(s: PrintStatement) {
        s.expression.accept(this)
    }

    override fun visit(s: PrintlnStatement) {
        s.expression.accept(this)
    }

    override fun visit(s: ReturnStatement) {
        s.expression.accept(this)
    }

    override fun visit(s: TernaryExpression) {
        s.condition.accept(this)
        s.trueExpr.accept(this)
        s.falseExpr.accept(this)
    }

    override fun visit(s: UnaryExpression) {
        s.expr1.accept(this)
    }

    override fun visit(s: ValueExpression) = Unit
    override fun visit(s: VariableExpression) = Unit

    override fun visit(st: WhileStatement) {
        st.condition.accept(this)
        st.statement.accept(this)
    }

    override fun visit(st: UsingStatement) {
        st.expression.accept(this)
    }
}