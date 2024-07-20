package com.kingmang.lazurite.patterns.visitor;


import com.kingmang.lazurite.parser.AST.Expressions.*;
import com.kingmang.lazurite.parser.AST.Statements.*;

public interface Visitor {

    void visit(AssertStatement s);
    void visit(TryCatchStatement s);
    void visit(ArrayExpression s);
    void visit(AssignmentExpression s);
    void visit(BinaryExpression s);
    void visit(BlockStatement s);
    void visit(BreakStatement s);
    void visit(ClassDeclarationStatement s);
    void visit(ConditionalExpression s);
    void visit(ContainerAccessExpression s);
    void visit(ContinueStatement s);

    void visit(DestructuringAssignmentStatement s);
    void visit(ForStatement s);
    void visit(ForeachAStatement s);
    void visit(ForeachMStatement s);
    void visit(FunctionDefineStatement s);
    void visit(FunctionReferenceExpression e);
    void visit(ExprStatement s);
    void visit(FunctionalExpression s);
    void visit(IfStatement s);
    void visit(MapExpression s);
    void visit(MatchExpression s);
    void visit(ObjectCreationExpression s);
    void visit(PrintStatement s);
    void visit(PrintlnStatement s);
    void visit(ReturnStatement s);
    void visit(TernaryExpression s);
    void visit(UnaryExpression s);
    void visit(ValueExpression s);
    void visit(VariableExpression s);
    void visit(WhileStatement st);
    void visit(UsingStatement st);
}
