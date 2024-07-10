package com.kingmang.lazurite.patterns.visitor;

import com.kingmang.lazurite.parser.AST.Expressions.*;
import com.kingmang.lazurite.parser.AST.Statements.*;

import java.util.Map;

public abstract class AbstractVisitor implements Visitor {

    @Override
    public void visit(ArrayExpression s) {
        for (Expression index : s.elements()) {
            index.accept(this);
        }
    }

    @Override
    public void visit(AssignmentExpression s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(BinaryExpression s) {
        s.expr1().accept(this);
        s.expr2().accept(this);
    }

    @Override
    public void visit(BlockStatement s) {
        for (Statement statement : s.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(BreakStatement s) {
    }

    @Override
    public void visit(ClassDeclarationStatement s) {
        
    }

    @Override
    public void visit(ConditionalExpression s) {
        s.expr1().accept(this);
        s.expr2().accept(this);
    }
    
    @Override
    public void visit(ContainerAccessExpression s) {
        s.root.accept(this);
        for (Expression index : s.indices) {
            index.accept(this);
        }
    }

    @Override
    public void visit(TryCatchStatement s){

    }

    @Override
    public void visit(ContinueStatement s) {
    }
    
    @Override
    public void visit(DestructuringAssignmentStatement s) {
        s.containerExpression.accept(this);
    }




    @Override
    public void visit(ForStatement s) {
        s.initialization.accept(this);
        s.termination.accept(this);
        s.increment.accept(this);
        s.statement.accept(this);
    }
    
    @Override
    public void visit(ForeachAStatement s) {
        s.container.accept(this);
        s.body.accept(this);
    }
    
    @Override
    public void visit(ForeachMStatement s) {
        s.container.accept(this);
        s.body.accept(this);
    }

    @Override
    public void visit(FunctionDefineStatement s) {
        s.body().accept(this);
    }

    @Override
    public void visit(DPointExpression e) {
    }

    @Override
    public void visit(ExprStatement s) {
        s.expr.accept(this);
    }

    @Override
    public void visit(FunctionalExpression s) {
        s.functionExpr.accept(this);
        for (Expression argument : s.arguments) {
            argument.accept(this);
        }
    }

    @Override
    public void visit(IfStatement s) {
        s.expression.accept(this);
        s.ifStatement.accept(this);
        if (s.elseStatement != null) {
            s.elseStatement.accept(this);
        }
    }

    
    @Override
    public void visit(MapExpression s) {
        for (Map.Entry<Expression, Expression> entry : s.elements().entrySet()) {
            entry.getKey().accept(this);
            entry.getValue().accept(this);
        }
    }
    
    @Override
    public void visit(MatchExpression s) {
        s.expression.accept(this);
    }
    
    @Override
    public void visit(ObjectCreationExpression s) {
        for (Expression argument : s.constructorArguments()) {
            argument.accept(this);
        }
    }

    @Override
    public void visit(PrintStatement s) {
        s.expression.accept(this);
    }
    
    @Override
    public void visit(PrintlnStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(ReturnStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(TernaryExpression s) {
        s.condition().accept(this);
        s.trueExpr().accept(this);
        s.falseExpr().accept(this);
    }
    
    @Override
    public void visit(UnaryExpression s) {
        s.expr1.accept(this);
    }

    @Override
    public void visit(ValueExpression s) {
    }

    @Override
    public void visit(VariableExpression s) {
    }

    @Override
    public void visit(WhileStatement st) {
        st.condition.accept(this);
        st.statement.accept(this);
    }

    @Override
    public void visit(UsingStatement st) {
        st.expression.accept(this);
    }
}