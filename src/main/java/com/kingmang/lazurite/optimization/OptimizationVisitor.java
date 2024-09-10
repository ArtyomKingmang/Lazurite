package com.kingmang.lazurite.optimization;


import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.parser.ast.Arguments;
import com.kingmang.lazurite.parser.ast.Argument;
import com.kingmang.lazurite.parser.ast.Accessible;
import com.kingmang.lazurite.parser.ast.ArgumentsBuilder;
import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.expressions.*;
import com.kingmang.lazurite.parser.ast.statements.*;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.runtime.UserDefinedFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OptimizationVisitor<T> implements ResultVisitor<Node, T> {

    @Override
    public Node visit(ArrayExpression s, T t) {
        final List<Expression> elements = new ArrayList<>(s.getElements().size());
        boolean changed = false;
        for (Expression expression : s.getElements()) {
            final Node node = expression.accept(this, t);
            if (node != expression) {
                changed = true;
            }
            elements.add((Expression) node);
        }
        if (changed) {
            return new ArrayExpression(elements);
        }
        return s;
    }

    @Override
    public Node visit(AssignmentExpression s, T t) {
        final Node exprNode = s.getExpression().accept(this, t);
        final Node targetNode = s.getTarget().accept(this, t);
        if ( (exprNode != s.getExpression() || targetNode != s.getTarget()) && (targetNode instanceof Accessible) ) {
            return new AssignmentExpression(s.getOperation(), (Accessible) targetNode, (Expression) exprNode);
        }
        return s;
    }

    @Override
    public Node visit(BinaryExpression s, T t) {
        final Node expr1 = s.getExpr1().accept(this, t);
        final Node expr2 = s.getExpr2().accept(this, t);
        if (expr1 != s.getExpr1() || expr2 != s.getExpr2()) {
            return new BinaryExpression(s.getOperation(), (Expression) expr1, (Expression) expr2);
        }
        return s;
    }

    @Override
    public Node visit(BlockStatement s, T t) {
        boolean changed = false;
        final BlockStatement result = new BlockStatement();
        for (Statement statement : s.getStatements()) {
            final Node node = statement.accept(this, t);
            if (node != statement) {
                changed = true;
            }
            if (node instanceof Statement) {
                result.add((Statement) node);
            } else if (node instanceof Expression) {
                result.add(new ExprStatement((Expression) node));
            }
        }
        if (changed) {
            return result;
        }
        return s;
    }

    @Override
    public Node visit(BreakStatement s, T t) {
        return s;
    }

    @Override
    public Node visit(ClassDeclarationStatement s, T t) {
        return s;
    }

    @Override
    public Node visit(ConditionalExpression s, T t) {
        final Node expr1 = s.getExpr1().accept(this, t);
        final Node expr2 = s.getExpr2().accept(this, t);
        if (expr1 != s.getExpr1() || expr2 != s.getExpr2()) {
            return new ConditionalExpression(s.getOperation(), (Expression) expr1, (Expression) expr2);
        }
        return s;
    }

    @Override
    public Node visit(ContainerAccessExpression s, T t) {
        final Node root = s.getRoot().accept(this, t);
        boolean changed = (root != s.getRoot());

        final List<Expression> indices = new ArrayList<>(s.getIndices().size());
        for (Expression expression : s.getIndices()) {
            final Node node = expression.accept(this, t);
            if (node != expression) {
                changed = true;
            }
            indices.add((Expression) node);
        }
        if (changed) {
            return new ContainerAccessExpression((Expression) root, indices);
        }
        return s;
    }

    @Override
    public Node visit(ContinueStatement s, T t) {
        return s;
    }


    @Override
    public Node visit(DestructuringAssignmentStatement s, T t) {
        final Node expr = s.getContainerExpression().accept(this, t);
        if (expr != s.getContainerExpression()) {
            return new DestructuringAssignmentStatement(s.getVariables(), (Expression) expr);
        }
        return s;
    }

    @Override
    public Node visit(ForStatement s, T t) {
        final Node initialization = s.getInitialization().accept(this, t);
        final Node termination = s.getTermination().accept(this, t);
        final Node increment = s.getIncrement().accept(this, t);
        final Node statement = s.getStatement().accept(this, t);
        if (initialization != s.getInitialization() || termination != s.getTermination()
                || increment != s.getIncrement() || statement != s.getStatement()) {
            return new ForStatement(consumeStatement(initialization),
                    (Expression) termination, consumeStatement(increment), consumeStatement(statement));
        }
        return s;
    }

    @Override
    public Node visit(ForeachArrayStatement s, T t) {
        final Node container = s.getContainer().accept(this, t);
        final Node body = s.getBody().accept(this, t);
        if (container != s.getContainer() || body != s.getBody()) {
            return new ForeachArrayStatement(s.getVariable(), (Expression) container, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(ForeachMapStatement s, T t) {
        final Node container = s.getContainer().accept(this, t);
        final Node body = s.getBody().accept(this, t);
        if (container != s.getContainer() || body != s.getBody()) {
            return new ForeachMapStatement(s.getKey(), s.getValue(), (Expression) container, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(FunctionDefineStatement s, T t) {
        final Arguments newArgs = new ArgumentsBuilder().build();
        boolean changed = visit(s.getArguments(), newArgs, t);

        final Node body = s.getBody().accept(this, t);
        if (changed || body != s.getBody()) {
            return new FunctionDefineStatement(s.getName(), newArgs, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(FunctionReferenceExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(ExprStatement s, T t) {
        final Node expr = s.getExpr().accept(this, t);
        if (expr != s.getExpr()) {
            return new ExprStatement((Expression) expr);
        }
        return s;
    }

    @Override
    public Node visit(FunctionalExpression s, T t) {
        final Node functionExpr = s.getFunctionExpr().accept(this, t);
        final FunctionalExpression result = new FunctionalExpression((Expression) functionExpr);
        boolean changed = functionExpr != s.getFunctionExpr();
        for (Expression argument : s.getArguments()) {
            final Node expr = argument.accept(this, t);
            if (expr != argument) {
                changed = true;
            }
            result.addArgument((Expression) expr);
        }
        if (changed) {
            return result;
        }
        return s;
    }

    @Override
    public Node visit(IfStatement s, T t) {
        final Node expression = s.getExpression().accept(this, t);
        final Node ifStatement = s.getIfStatement().accept(this, t);
        final Node elseStatement;
        if (s.getElseStatement() != null) {
            elseStatement = s.getElseStatement().accept(this, t);
        } else {
            elseStatement = null;
        }
        if (expression != s.getExpression() || ifStatement != s.getIfStatement() || elseStatement != s.getElseStatement()) {
            return new IfStatement((Expression) expression, consumeStatement(ifStatement),
                    (elseStatement == null ? null : consumeStatement(elseStatement)) );
        }
        return s;
    }


    @Override
    public Node visit(MapExpression s, T t) {
        final Map<Expression, Expression> elements = new HashMap<>(s.getElements().size());
        boolean changed = false;
        for (Map.Entry<Expression, Expression> entry : s.getElements().entrySet()) {
            final Node key = entry.getKey().accept(this, t);
            final Node value = entry.getValue().accept(this, t);
            if (key != entry.getKey() || value != entry.getValue()) {
                changed = true;
            }
            elements.put((Expression) key, (Expression) value);
        }
        if (changed) {
            return new MapExpression(elements);
        }
        return s;
    }

    @Override
    public Node visit(MatchExpression s, T t) {
        // now not implemented :(
        return null;
    }

    @Override
    public Node visit(ObjectCreationExpression s, T t) {
        final List<Expression> args = new ArrayList<>();
        boolean changed = false;
        for (Expression argument : s.getConstructorArguments()) {
            final Node expr = argument.accept(this, t);
            if (expr != argument) {
                changed = true;
            }
            args.add((Expression) expr);
        }

        if (changed) {
            return new ObjectCreationExpression(s.getClassName(), args);
        }
        return s;
    }

    @Override
    public Node visit(PrintStatement s, T t) {
        final Node expression = s.getExpression().accept(this, t);
        if (expression != s.getExpression()) {
            return new PrintStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(PrintlnStatement s, T t) {
        final Node expression = s.getExpression().accept(this, t);
        if (expression != s.getExpression()) {
            return new PrintlnStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(ReturnStatement s, T t) {
        final Node expression = s.getExpression().accept(this, t);
        if (expression != s.getExpression()) {
            return new ReturnStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(TernaryExpression s, T t) {
        final Node condition = s.getCondition().accept(this, t);
        final Node trueExpr = s.getTrueExpr().accept(this, t);
        final Node falseExpr = s.getFalseExpr().accept(this, t);
        if (condition != s.getCondition() || trueExpr != s.getTrueExpr() || falseExpr != s.getFalseExpr()) {
            return new TernaryExpression((Expression) condition, (Expression) trueExpr, (Expression) falseExpr);
        }
        return s;
    }

    @Override
    public Node visit(UnaryExpression s, T t) {
        final Node expr1 = s.getExpr1().accept(this, t);
        if (expr1 != s.getExpr1()) {
            return new UnaryExpression(s.getOperation(), (Expression) expr1);
        }
        return s;
    }

    @Override
    public Node visit(ValueExpression s, T t) {
        if ( (s.getValue().type() == Types.FUNCTION) && (s.getValue().raw() instanceof UserDefinedFunction) ) {
            final UserDefinedFunction function = (UserDefinedFunction) s.getValue().raw();
            final UserDefinedFunction accepted = visit(function, t);
            if (accepted != function) {
                return new ValueExpression(accepted);
            }
        }
        return s;
    }

    @Override
    public Node visit(VariableExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(WhileStatement s, T t) {
        final Node condition = s.getCondition().accept(this, t);
        final Node statement = s.getStatement().accept(this, t);
        if (condition != s.getCondition() || statement != s.getStatement()) {
            return new WhileStatement((Expression) condition, consumeStatement(statement));
        }
        return s;
    }

    @Override
    public Node visit(UsingStatement s, T t) {
        final Node expression = s.getExpression().accept(this, t);
        if (expression != s.getExpression()) {
            return new UsingStatement((Expression) expression);
        }
        return s;
    }

    public UserDefinedFunction visit(UserDefinedFunction s, T t) {
        final Arguments newArgs = new ArgumentsBuilder().build();
        boolean changed = visit(s.getArguments(), newArgs, t);

        final Node body = s.getBody().accept(this, t);
        if (changed || body != s.getBody()) {
            return new UserDefinedFunction(newArgs, consumeStatement(body));
        }
        return s;
    }



    protected boolean visit(final Arguments in, Arguments out, T t) {
        boolean changed = false;
        ArgumentsBuilder builder = new ArgumentsBuilder();
        for (Argument argument : in) {
            final Expression valueExpr = argument.getValueExpr();
            if (valueExpr == null) {
                builder.addRequired(argument.getName());
            } else {
                final Node expr = valueExpr.accept(this, t);
                if (expr != valueExpr) {
                    changed = true;
                }
                builder.addOptional(argument.getName(), (Expression) expr);
            }
        }
        out = builder.build();
        return changed;
    }

    protected Statement consumeStatement(Node node) {
        if (node instanceof Statement) {
            return (Statement) node;
        }
        return new ExprStatement((Expression) node);
    }
}
