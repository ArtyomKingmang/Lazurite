package com.kingmang.lazurite.parser.AST.Statements;


import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ForStatement extends InterruptableNode implements Statement {
    
    public final Statement initialization;
    public final Expression termination;
    public final Statement increment;
    public final Statement statement;

    @Override
    public void execute() {
        super.interruptionCheck();
        for (initialization.execute(); termination.eval().asInt() != 0; increment.execute()) {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "for " + initialization + ", " + termination + ", " + increment + " " + statement;
    }
}
