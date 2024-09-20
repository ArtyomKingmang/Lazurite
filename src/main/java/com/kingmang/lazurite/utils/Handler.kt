package com.kingmang.lazurite.utils

import com.kingmang.lazurite.console.Console
import com.kingmang.lazurite.core.CallStack
import com.kingmang.lazurite.crashHandler.CrashHandler
import com.kingmang.lazurite.crashHandler.reporter.impl.SimpleCrashReporter
import com.kingmang.lazurite.crashHandler.reporter.output.impl.ConsoleReportOutput
import com.kingmang.lazurite.crashHandler.reporter.output.impl.FileReportOutput
import com.kingmang.lazurite.crashHandler.reporter.processors.impl.SourceCodeProcessor
import com.kingmang.lazurite.crashHandler.reporter.processors.impl.TokensProcessor
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.optimization.Optimizer
import com.kingmang.lazurite.parser.ast.statements.BlockStatement
import com.kingmang.lazurite.parser.ILexer
import com.kingmang.lazurite.parser.IParser
import com.kingmang.lazurite.parser.ast.statements.Statement
import com.kingmang.lazurite.parser.impl.LexerImplementation
import com.kingmang.lazurite.parser.impl.ParserImplementation
import com.kingmang.lazurite.parser.preprocessor.Preprocessor
import com.kingmang.lazurite.patterns.visitor.FunctionAdder
import com.kingmang.lazurite.runtime.Libraries
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

object Handler {
    @JvmStatic
    @Throws(IOException::class)
    fun run(path: String, optLvl: Int, printResultOfOptimization: Boolean) {
        Libraries.add(path)
        runProgram(Loader.readSource(path), path, optLvl, printResultOfOptimization)
    }

    @JvmStatic
    @Throws(IOException::class)
    fun run(path: String, showTokens: Boolean) {
        val source = Loader.readSource(path)
        handle(source, source, true, showTokens)
    }

    @JvmStatic
    fun runProgram(code: String, file: String, optLvl : Int, printResultOfOptimization: Boolean) {
        CrashHandler.register(
            SimpleCrashReporter(),
            ConsoleReportOutput(),
            FileReportOutput()
        )

        try {
            val input = Preprocessor.preprocess(code)
            CrashHandler.getCrashReporter().addProcessor(SourceCodeProcessor(input))

            val lexer: ILexer = LexerImplementation(input)

            val tokens = lexer.tokenize()
            CrashHandler.getCrashReporter().addProcessor(TokensProcessor(tokens))

            val parser: IParser =
                ParserImplementation(tokens, file)
            val parsedProgram = parser.parse()
            if (parser.parseErrors.hasErrors()) {
                println(parser.parseErrors)
                return
            }
            val program : Statement;
            //if(optLvl > 0) {
               // program = Optimizer.optimize(parsedProgram, optLvl, printResultOfOptimization);
            //}else
                program = parsedProgram;

            program.accept(FunctionAdder())

            try {
                program.execute()
            } catch (ex: LzrException) {
                ex.print(System.err)
            } catch (throwable: Throwable) {
                CrashHandler.proceed(throwable)
            }
        } catch (throwable: Throwable) {
            CrashHandler.proceed(throwable)
        }
    }

    @JvmStatic
    fun handle(code: String, pathToScript: String, isExec: Boolean, showTokens: Boolean) {
        try {
            if (!isExec) {
                Log.clear()
                Log.append("Run (${Date()})\n")
            }
            val input = Preprocessor.preprocess(code)
            val tokens = LexerImplementation(input).tokenize()
            if (showTokens) {
                println("---Tokens---")
                for ((type, text) in tokens) {
                    println("$type\t$text")
                }
                println("---Result---")
            }
            val program = ParserImplementation(
                tokens,
                pathToScript
            ).parse() as BlockStatement
            program.execute()
            if (!isExec) {
                Variables.clear()
            }
        } catch (ex: LzrException) {
            Log.append("${ex.type}: ${ex.message} in $pathToScript (${Date()})\n")
            ex.print(System.err)
            if (!isExec) {
                Variables.clear()
            }
            var count = CallStack.getCalls().size
            if (count == 0) return
            println("\nCall stack was:")
            for (info in CallStack.getCalls()) {
                println("    $count. $info")
                count--
            }
        }
    }

    fun returnHandle(input: String, pathToScript: String): LzrValue {
        try {
            val tokens = LexerImplementation(input).tokenize()
            val program = ParserImplementation(
                tokens,
                pathToScript
            ).parseExpression()
            return program.eval()
        } catch (ex: LzrException) {
            ex.print(System.err)
            return LzrNumber.ZERO
        }
    }
}