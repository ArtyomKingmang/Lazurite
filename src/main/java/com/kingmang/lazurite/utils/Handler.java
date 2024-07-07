package com.kingmang.lazurite.utils;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.core.CallStack;
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.parser.parse.Lexer;
import com.kingmang.lazurite.parser.parse.Parser;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import com.kingmang.lazurite.parser.parse.classes.ParserImplementation;
import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.parser.preprocessor.Preprocessor;
import com.kingmang.lazurite.patterns.visitor.FunctionAdder;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.runtime.Variables;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Handler {

    public static void Run(String path) throws IOException {
        Handler.RunProgram(Loader.readSource(path));
    }

    public static void Run(String path, boolean showTokens) throws IOException {
        Handler.handle(Loader.readSource(path), Loader.readSource(path), true, showTokens);
    }

    public static void RunProgram (String code) throws IOException {
        String input = Preprocessor.preprocess(code);
        Lexer lexer = new LexerImplementation(input);
        final List<Token> tokens = lexer.tokenize();
        final Parser parser = new ParserImplementation(tokens);
        final Statement parsedProgram = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            System.out.println(parser.getParseErrors());
            return;
        }
        final Statement program;
        program = parsedProgram;
        program.accept(new FunctionAdder());

        try {
            program.execute();
        } catch (LzrException ex) {
            System.out.println(String.format("%s: %s in: \n" + Ansi.ansi().fg(Ansi.Color.GREEN).a("%s").reset(), ex.getType(), ex.getText(), input));
            //Console.handleException(Thread.currentThread(), ex);
        }

    }

    public static void handle(String input, String pathToScript, boolean isExec, boolean showTokens) {
        try {
            try{
                if(!isExec) {
                    Log.clear();
                    Log.append(String.format("Run (%s)\n", new Date()));
                }
            }
            catch (Exception ignored){}
            input = Preprocessor.preprocess(input);
            final List<Token> tokens = new LexerImplementation(input).tokenize();
            if(showTokens) {
                System.out.println("---Tokens---");
                for (Token t : tokens) {
                    System.out.println(t.getType() + "\t" + t.getText());
                }
                System.out.println("---Result---");
            }
            final BlockStatement program = (BlockStatement) new ParserImplementation(tokens).parse();
            program.execute();
            if(!isExec){
                Variables.clear();
            }
        } catch (LzrException ex) {
            try{
                Log.append(String.format("%s: %s in %s (%s)\n", ex.getType(), ex.getText(), pathToScript, new Date()));
            }
            catch (Exception ex2){}
            System.out.println(String.format("%s: %s in %s", ex.getType(), ex.getText(), pathToScript));
            if(!isExec) {
                Variables.clear();
            }
            int count = CallStack.getCalls().size();
            if (count == 0) return;
            System.out.println(String.format("\nCall stack was:"));
            for (CallStack.CallInfo info : CallStack.getCalls()) {
                System.out.println("    " + count + ". " + info);
                count--;
            }
        }
    }

    public static LzrValue returnHandle(String input, String pathToScript) {
        try {
            final List<Token> tokens = new LexerImplementation(input).tokenize();
            final Expression program = new ParserImplementation(tokens).parseExpr();
            return program.eval();
        } catch (LzrException ex) {
            System.out.println(String.format("%s: %s in %s", ex.getType(), ex.getText(), pathToScript));
            return LzrNumber.ZERO;
        }
    }

}