package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import com.kingmang.lazurite.parser.parse.classes.ParserImplementation;
import com.kingmang.lazurite.utils.Loader;
import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.patterns.visitor.FunctionAdder;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public final class IncludeStatement extends InterruptableNode implements Statement {

    public final Expression expression;

    @Override
    public void execute() {
        super.interruptionCheck();
        try {
            final Statement program = loadProgram(expression.eval().asString());
            program.accept(new FunctionAdder());
            program.execute();
        } catch (Exception ex) {
            throw new LzrException(ex.toString(), ex.getMessage());
        }
    }

    public Statement loadProgram(String path) throws IOException {
        final String input = Loader.readSource(path);
        final List<Token> tokens = LexerImplementation.tokenize(input);
        final ParserImplementation parser = new ParserImplementation(tokens);
        final Statement program = parser.parse(tokens);
        if (parser.getParseErrors().hasErrors()) {
            throw new LzrException("ParseException ", parser.getParseErrors().toString());
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
