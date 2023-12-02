package com.kingmang.lazurite;

import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.CallStack;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.pars.*;
import com.kingmang.lazurite.parser.ast.MStatement;
import com.kingmang.lazurite.parser.ast.Expression;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.LZREx.parenErrorCheckOptimize;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Handler {

    public static void handle(String input, String pathToScript, boolean isExec) {
        try {
            try{
                if(!isExec) {
                    log.clear();
                    log.append(String.format("Run (%s)\n", new Date()));
                }
            }
            catch (Exception ignored){}
            final List<Token> tokens = new Lexer(input).tokenize();
            final MStatement program = (MStatement) new Parser(tokens).parse();
            program.execute();
            if(!isExec){
                Variables.clear();
            }
        } catch (LZRException ex) {
            try{
                log.append(String.format("%s: %s in %s (%s)\n", ex.getType(), ex.getText(), pathToScript, new Date()));
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
    public static Value returnHandle(String input, String pathToScript) {
        try {
            final List<Token> tokens = new Lexer(input).tokenize();
            final Expression program = new Parser(tokens).expression();
            return program.eval();
        } catch (LZRException ex) {
            System.out.println(String.format("%s: %s in %s", ex.getType(), ex.getText(), pathToScript));
            return LZRNumber.ZERO;
        }
    }

    public static void RunProgram (String input) throws IOException {

        final List<Token> tokens = Lexer.tokenize(input);
        final Parser parser = new Parser(tokens);
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
        } catch (Exception ex) {
            Console.handleException(Thread.currentThread(), ex);
        }

    }

}