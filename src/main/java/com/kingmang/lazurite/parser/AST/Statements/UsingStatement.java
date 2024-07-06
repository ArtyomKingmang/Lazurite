package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LzrException;

import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.parser.AST.InterruptableNode;
import com.kingmang.lazurite.parser.parse.Parser;
import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import com.kingmang.lazurite.parser.parse.classes.ParserImplementation;
import com.kingmang.lazurite.patterns.visitor.FunctionAdder;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runner.Runner;
import com.kingmang.lazurite.runner.RunnerInfo;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.utils.Loader;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;


@RequiredArgsConstructor
public final class UsingStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "com.kingmang.lazurite.libraries.%s.%s.%s.%s";

    
    public final Expression expression;

    @Override
    public void execute() {
        super.interruptionCheck();
        final LzrValue value = expression.eval();
        String[] parts = value.asString().split("\\.");
        //load Lzr file
        try {
            try {

                final Statement program = loadLzrLibrary(value.asString());
                program.accept(new FunctionAdder());
                program.execute();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }catch (Exception e){
            //load Lzr libs
            try{
                String libPackage = parts[0];

                try {
                    String libName = parts[1];
                    loadModule(libPackage, libName);

                }catch (Exception libEx){
                    String libChild = parts[1];
                    String libName = parts[2];
                    loadModule(libPackage, libChild, libName);
                }
            //load .jar libs
            }catch (Exception libEx){
                String[] jarParts = value.asString().split("::");
                String nameOfLib = jarParts[0] + ".jar";
                String pathToLib = jarParts[1];
                try {
                    URLClassLoader child = new URLClassLoader(
                            new URL[] { new URL("file:" + RunnerInfo.getPathToLzrLibs() + nameOfLib) },
                            this.getClass().getClassLoader()
                    );
                    Library module;
                    try {
                        module = (Library) Class.forName(pathToLib, true, child).newInstance();
                    } catch (ClassNotFoundException ex) {
                        module = (Library) Class.forName(pathToLib + ".invoker", true, child).newInstance();
                    }
                    module.init();
                } catch (MalformedURLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                    throw new LzrException(ex.toString(), ex.getMessage());
                }
            }
        }
    }

    private void loadModule(String libPackage, String libChild, String name) {
        try {
            final Library module = (Library) Class.forName(String.format(PACKAGE, libPackage, libChild,name, name)).newInstance();
            module.init();
        } catch (Exception ex) {
            throw new LzrException("RuntimeException", "Unable to load module " + name + "\n" + ex);


        }
    }
    public Statement loadLzrLibrary(String path) throws IOException {
        final String input = Loader.readSource(path);
        //String input = "func danu(){print(111)}";
        final List<Token> tokens = LexerImplementation.tokenize(input);
        final Parser parser = new ParserImplementation(tokens);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            throw new LzrException("ParseException ", parser.getParseErrors().toString());
        }
        return program;
    }

    private void loadModule(String libPackage, String name) {
            final Library module;
            try {
                module = (Library) Class.forName(String.format("com.kingmang.lazurite.libraries.%s.%s.%s", libPackage,name, name)).newInstance();
                module.init();
            } catch (Exception e) {
                throw new LzrException("RuntimeException", "Unable to load module " + name + "\n" + e);
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
        return "using " + expression;
    }
}
