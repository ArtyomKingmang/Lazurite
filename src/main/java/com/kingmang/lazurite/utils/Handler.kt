package com.kingmang.lazurite.utils

import com.kingmang.lazurite.core.CallStack
import com.kingmang.lazurite.crashHandler.CrashHandler
import com.kingmang.lazurite.crashHandler.reporter.crashReporterImplementation.SimpleCrashReporter
import com.kingmang.lazurite.crashHandler.reporter.output.impl.ConsoleReportOutput
import com.kingmang.lazurite.crashHandler.reporter.output.impl.FileReportOutput
import com.kingmang.lazurite.crashHandler.reporter.processors.impl.SourceCodeProcessor
import com.kingmang.lazurite.crashHandler.reporter.processors.impl.TokensProcessor
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.parser.AST.Statements.BlockStatement
import com.kingmang.lazurite.parser.ILexer
import com.kingmang.lazurite.parser.IParser
import com.kingmang.lazurite.parser.lexerImplementations.LexerImplementation
import com.kingmang.lazurite.parser.parserImplementations.ParserImplementation
import com.kingmang.lazurite.parser.preprocessor.Preprocessor
import com.kingmang.lazurite.patterns.visitor.FunctionAdder
import com.kingmang.lazurite.runtime.Libraries
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import org.fusesource.jansi.Ansi
import java.util.*

object Handler {

    @JvmStatic
    fun Run(path: String) {
        Libraries.add(path)
        runProgram(Loader.readSource(path))
    }

    @JvmStatic
    fun run(path: String, showTokens: Boolean) {
        val source = Loader.readSource(path)
        handle(source, source, true, showTokens)
    }

    @JvmStatic
    fun runProgram(code: String) {
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

            val parser: IParser = ParserImplementation(tokens)
            val parsedProgram = parser.parse()
            if (parser.parseErrors.hasErrors()) {
                println(parser.parseErrors)
                return
            }
            parsedProgram.accept(FunctionAdder())

            try {
                parsedProgram.execute()
            } catch (ex: LzrException) {
                val ansiInput = Ansi.ansi().fg(Ansi.Color.GREEN).a(input).reset()
                println("${ex.type}: ${ex.text} in $ansiInput")
                //Console.handleException(Thread.currentThread(), ex);
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
            val program = ParserImplementation(tokens).parse() as BlockStatement
            program.execute()
            if (!isExec) {
                Variables.clear()
            }
        } catch (ex: LzrException) {
            Log.append("${ex.type}: ${ex.text} in $pathToScript (${Date()})\n")
            println("${ex.type}: ${ex.text} in $pathToScript")
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
            val program = ParserImplementation(tokens).parseExpression()
            return program.eval()
        } catch (ex: LzrException) {
            println("${ex.type}: ${ex.text} in $pathToScript")
            return LzrNumber.ZERO
        }
    }
}