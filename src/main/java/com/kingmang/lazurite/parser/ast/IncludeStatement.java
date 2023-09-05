package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.LZREx.LzrExeption;
import com.kingmang.lazurite.parser.pars.Lexer;
import com.kingmang.lazurite.parser.pars.Parser;
import com.kingmang.lazurite.parser.pars.SourceLoader;
import com.kingmang.lazurite.parser.pars.Token;
import com.kingmang.lazurite.parser.pars.FunctionAdder;

import java.io.IOException;
import java.util.List;


public final class IncludeStatement extends InterruptableNode implements Statement {

    public final Expression expression;
    
    public IncludeStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        try {
            final Statement program = loadProgram(expression.eval().asString());
            if (program != null) {
                program.accept(new FunctionAdder());
                program.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Statement loadProgram(String path) throws IOException {
        final String input = SourceLoader.readSource(path);
        final List<Token> tokens = Lexer.tokenize(input);
        final Parser parser = new Parser(tokens);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            throw new LzrExeption("ParseException ",parser.getParseErrors().toString());
        }
        return program;
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
        return "include " + expression;
    }
}
