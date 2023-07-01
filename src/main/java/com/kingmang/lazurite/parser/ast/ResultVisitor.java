package com.kingmang.lazurite.parser.ast;


public interface ResultVisitor<R, T> {
    
    R visit(ArrayExpression s, T t);
    R visit(AssignmentExpression s, T t);
    R visit(BinaryExpression s, T t);
    R visit(MStatement s, T t);
    R visit(BreakStatement s, T t);
    R visit(ClassDeclarationStatement s, T t);
    R visit(ConditionalExpression s, T t);
    R visit(ContainerAccessExpression s, T t);
    R visit(ContinueStatement s, T t);

    R visit(DestructuringAssignmentStatement s, T t);
    R visit(ForStatement s, T t);
    R visit(ForeachAStatement s, T t);
    R visit(ForeachMStatement s, T t);
    R visit(FunctionDefineStatement s, T t);
    R visit(DPointExpression s, T t);
    R visit(ExprStatement s, T t);
    R visit(FunctionalExpression s, T t);
    R visit(IfStatement s, T t);
    R visit(IncludeStatement s, T t);
    R visit(MapExpression s, T t);
    R visit(MatchExpression s, T t);
    R visit(ObjectCreationExpression s, T t);
    R visit(PrintStatement s, T t);
    R visit(PrintlnStatement s, T t);
    R visit(ReturnStatement s, T t);
    R visit(TernaryExpression s, T t);
    R visit(UnaryExpression s, T t);
    R visit(ValueExpression s, T t);
    R visit(VariableExpression s, T t);
    R visit(WhileStatement s, T t);
    R visit(UsingStatement s, T t);
}
