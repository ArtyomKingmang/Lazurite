package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

public final class AssignmentStatement implements Statement {

    private final String variable;
    private final Expression expression;
    private final int mode;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
        this.mode = 0;
    }
    public AssignmentStatement(String variable, Expression expression, int mode) {
        this.variable = variable;
        this.expression = expression;
        this.mode = mode;
    }

    @Override
    public void execute() {
        if (mode == 0){
            final Value result = expression.eval();
            Variables.set(variable, result);
        }
        else {
            try{
                // Is number
                Integer.parseInt(Variables.get(variable).asString());
                // =========
                if (mode == 1){
                    final Value result = expression.eval();
                    Variables.set(variable, LZRNumber.of(Variables.get(variable).asInt() + result.asInt()));
                }
                else if(mode == 2){
                    final Value result = expression.eval();
                    Variables.set(variable, LZRNumber.of(Variables.get(variable).asInt() - result.asInt()));
                }
                else if(mode == 3){
                    final Value result = expression.eval();
                    Variables.set(variable, LZRNumber.of(Variables.get(variable).asInt() * result.asInt()));
                }
                else if(mode == 4){
                    final Value result = expression.eval();
                    Variables.set(variable, LZRNumber.of(Variables.get(variable).asInt() / result.asInt()));
                }
            }
            catch (Exception ex){
                if (mode == 1){
                    final Value result = expression.eval();
                    Variables.set(variable, new LZRString(Variables.get(variable).toString() + result.toString()));
                }
                else{
                    throw new LZRExeption("TypeError", "non-applicable operation to string");
                }
            }
        }
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getMode() {
        return mode;
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