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
import com.kingmang.lazurite.parser.AST.Statements.BreakStatement;
import com.kingmang.lazurite.parser.AST.Statements.ClassDeclarationStatement;
import com.kingmang.lazurite.parser.AST.Statements.ContinueStatement;
import com.kingmang.lazurite.parser.AST.Statements.DestructuringAssignmentStatement;
import com.kingmang.lazurite.parser.AST.Statements.ExprStatement;
import com.kingmang.lazurite.parser.AST.Statements.ForStatement;
import com.kingmang.lazurite.parser.AST.Statements.ForeachAStatement;
import com.kingmang.lazurite.parser.AST.Statements.ForeachMStatement;
import com.kingmang.lazurite.parser.AST.Statements.FunctionDefineStatement;
import com.kingmang.lazurite.parser.AST.Statements.IfStatement;
import com.kingmang.lazurite.parser.AST.Statements.IncludeStatement;
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement;
import com.kingmang.lazurite.parser.AST.Statements.PrintStatement;
import com.kingmang.lazurite.parser.AST.Statements.PrintlnStatement;
import com.kingmang.lazurite.parser.AST.Statements.ReturnStatement;
import com.kingmang.lazurite.parser.AST.Statements.UsingStatement;
import com.kingmang.lazurite.parser.AST.Statements.WhileStatement;

public interface Visitor {
    
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
    void visit(DPointExpression e);
    void visit(ExprStatement s);
    void visit(FunctionalExpression s);
    void visit(IfStatement s);
    void visit(IncludeStatement s);
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
