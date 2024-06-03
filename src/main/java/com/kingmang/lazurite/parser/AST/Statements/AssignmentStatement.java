package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import com.kingmang.lazurite.runtime.Types.LzrString;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Variables;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public final class AssignmentStatement implements Statement {

    @Getter
    private final String variable;
    @Getter
    private final Expression expression;
    @Getter
    private final int mode;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
        this.mode = 0;
    }

    @Override
    public void execute() {
        if (mode == 0){
            final LzrValue result = expression.eval();
            Variables.set(variable, result);
        }
        else {
            try{
                // Is number
                Integer.parseInt(Variables.get(variable).asString());
                // =========
                if (mode == 1){
                    final LzrValue result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() + result.asInt()));
                }
                else if(mode == 2){
                    final LzrValue result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() - result.asInt()));
                }
                else if(mode == 3){
                    final LzrValue result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() * result.asInt()));
                }
                else if(mode == 4){
                    final LzrValue result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() / result.asInt()));
                }
            }
            catch (Exception ex){
                if (mode == 1){
                    final LzrValue result = expression.eval();
                    Variables.set(variable, new LzrString(Variables.get(variable).toString() + result.toString()));
                }
                else{
                    throw new LzrException("TypeError", "non-applicable operation to string");
                }
            }
        }
    }


    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}