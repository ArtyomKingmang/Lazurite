package com.kingmang.lazurite.patterns.visitor;


import com.kingmang.lazurite.parser.AST.Expressions.ArrayExpression;
import com.kingmang.lazurite.parser.AST.Expressions.AssignmentExpression;
import com.kingmang.lazurite.parser.AST.Expressions.BinaryExpression;
import com.kingmang.lazurite.parser.AST.Expressions.ConditionalExpression;
import com.kingmang.lazurite.parser.AST.Expressions.ContainerAccessExpression;
import com.kingmang.lazurite.parser.AST.Expressions.DPointExpression;
import com.kingmang.lazurite.parser.AST.Expressions.FunctionalExpression;
import com.kingmang.lazurite.parser.AST.Expressions.MapExpression;
import com.kingmang.lazurite.parser.AST.Expressions.MatchExpression;
import com.kingmang.lazurite.parser.AST.Expressions.TernaryExpression;
import com.kingmang.lazurite.parser.AST.Expressions.UnaryExpression;
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression;
import com.kingmang.lazurite.parser.AST.Expressions.VariableExpression;
import com.kingmang.lazurite.parser.AST.Expressions.ObjectCreationExpression;
import com.kingmang.lazurite.parser.AST.Statements.*;

public interface ResultVisitor<R, T> {

    R visit(TryCatchStatement s, T t);
    R visit(ArrayExpression s, T t);
    R visit(AssignmentExpression s, T t);
    R visit(BinaryExpression s, T t);
    R visit(BlockStatement s, T t);
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
