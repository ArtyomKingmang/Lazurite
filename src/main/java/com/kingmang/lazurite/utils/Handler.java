package com.kingmang.lazurite.utils;

import com.kingmang.lazurite.core.CallStack;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.parser.ILexer;
import com.kingmang.lazurite.parser.IParser;
import com.kingmang.lazurite.parser.Token;
import com.kingmang.lazurite.parser.impl.LexerImplementation;
import com.kingmang.lazurite.parser.impl.ParserImplementation;
import com.kingmang.lazurite.parser.preprocessor.Preprocessor;
import com.kingmang.lazurite.patterns.visitor.FunctionAdder;
import com.kingmang.lazurite.runtime.Libraries;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import me.besstrunovpw.lazurite.crashhandler.CrashHandler;
import me.besstrunovpw.lazurite.crashhandler.reporter.ICrashReporter;
import me.besstrunovpw.lazurite.crashhandler.reporter.impl.SimpleCrashReporter;
import me.besstrunovpw.lazurite.crashhandler.reporter.processors.impl.TokensProcessor;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Handler {

    public static void Run(String path) throws IOException {
        Libraries.add(path);
        Handler.runProgram(Loader.readSource(path));
    }

    public static void run(@NotNull String path, boolean showTokens) throws IOException {
        Handler.handle(Loader.readSource(path), Loader.readSource(path), true, showTokens);
    }

    public static void runProgram(String code) throws IOException {
        CrashHandler.INSTANCE.register(new SimpleCrashReporter());

        try {
            String input = Preprocessor.preprocess(code);
            ILexer lexer = new LexerImplementation(input);

            final List<Token> tokens = lexer.tokenize();
            CrashHandler.INSTANCE.getCrashReporter().addProcessor(new TokensProcessor(tokens));

            final IParser parser = new ParserImplementation(tokens);
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
                System.out.printf("%s: %s in: \n" + Ansi.ansi().fg(Ansi.Color.GREEN).a("%s").reset() + "%n", ex.getType(), ex.getText(), input);
                //Console.handleException(Thread.currentThread(), ex);
            }
        }
        catch (Throwable throwable) {
            CrashHandler.INSTANCE.proceed(throwable);
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
            System.out.printf("%s: %s in %s%n", ex.getType(), ex.getText(), pathToScript);
            if(!isExec) {
                Variables.clear();
            }
            int count = CallStack.getCalls().size();
            if (count == 0) return;
            System.out.println("\nCall stack was:");
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
            System.out.printf("%s: %s in %s%n", ex.getType(), ex.getText(), pathToScript);
            return LzrNumber.ZERO;
        }
    }

}