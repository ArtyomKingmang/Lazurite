package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.parser.IParser
import com.kingmang.lazurite.parser.ast.InterruptableNode
import com.kingmang.lazurite.parser.ast.expressions.Expression
import com.kingmang.lazurite.parser.impl.LexerImplementation
import com.kingmang.lazurite.parser.impl.ParserImplementation
import com.kingmang.lazurite.patterns.visitor.FunctionAdder
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runner.RunnerInfo
import com.kingmang.lazurite.runtime.Libraries.add
import com.kingmang.lazurite.runtime.Libraries.isExists
import com.kingmang.lazurite.utils.Loader.readSource
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader

data class UsingStatement(val expression: Expression) : InterruptableNode(), Statement {
    override fun execute() {
        super.interruptionCheck()
        val value = expression.eval()
        val parts = value.asString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        //load Lzr file
        try {
            try {
                val file = value.asString()
                // Check
                if (isExists(file))
                    return
                add(file)
                // Load & Run
                val program = loadLzrLibrary(file)
                program.accept(FunctionAdder())
                program.execute()
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        } catch (e: Exception) {
            //load Lzr libs
            try {
                val libPackage = parts[0]

                try {
                    val libName = parts[1]
                    loadModule(libPackage, libName)
                } catch (libEx: Exception) {
                    val libChild = parts[1]
                    val libName = parts[2]
                    loadModule(libPackage, libChild, libName)
                }
                //load .jar libs
            } catch (libEx: Exception) {
                val jarParts = value.asString().split("::".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val nameOfLib = jarParts[0] + ".jar"
                val pathToLib = jarParts[1]
                try {
                    val child = URLClassLoader(arrayOf(URL("file:" + RunnerInfo.LZR_LIBS_PATH + nameOfLib)), this.javaClass.classLoader)
                    val module =
                        try {
                            Class.forName(pathToLib, true, child).newInstance() as Library
                        } catch (ex: ClassNotFoundException) {
                            Class.forName("$pathToLib.invoker", true, child).newInstance() as Library
                        }
                    module.init()
                } catch (ex: MalformedURLException) {
                    throw LzrException(ex.toString(), ex.message!!)
                } catch (ex: InstantiationException) {
                    throw LzrException(ex.toString(), ex.message!!)
                } catch (ex: IllegalAccessException) {
                    throw LzrException(ex.toString(), ex.message!!)
                } catch (ex: ClassNotFoundException) {
                    throw LzrException(ex.toString(), ex.message!!)
                }
            }
        }
    }

    private fun loadModule(libPackage: String, libChild: String, name: String) {
        try {
            val module = Class.forName(String.format(PACKAGE, libPackage, libChild, name, name)).newInstance() as Library
            module.init()
        } catch (ex: Exception) {
            throw LzrException("RuntimeException", "Unable to load module $name\n$ex")
        }
    }

    @Throws(IOException::class)
    fun loadLzrLibrary(path: String?): Statement {
        val input = readSource(path!!)
        val tokens = LexerImplementation.tokenize(input)
        val parser: IParser = ParserImplementation(tokens, path)
        val program = parser.parse()
        if (parser.parseErrors.hasErrors())
            throw LzrException("ParseException ", parser.parseErrors.toString())
        return program
    }

    private fun loadModule(libPackage: String, name: String) {
        try {
            val module = Class.forName(String.format(PACKAGE, libPackage, name, name)).newInstance() as Library
            module.init()
        } catch (e: Exception) {
            throw LzrException("RuntimeException", "Unable to load module $name\n$e")
        }
    }

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String =
        "using ${this.expression}"

    companion object {
        private const val PACKAGE = "com.kingmang.lazurite.libraries.%s.%s.%s.%s"
    }
}
