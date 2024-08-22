package com.kingmang.lazurite.editors

import org.fusesource.jansi.Ansi
import java.io.FileWriter
import java.io.IOException
import java.util.*

object Editor {
    fun openEditor() {
        val scanner = Scanner(System.`in`)
        var lineNumber = 1
        print("\u001b[H\u001b[2J")
        System.out.flush()
        println(
            Ansi.ansi().fg(Ansi.Color.BLUE).a("============================================================").reset()
        )
        println(Ansi.ansi().fg(Ansi.Color.BLUE).a("                 Lazurite code editor 0.1").reset())
        println(
            Ansi.ansi().fg(Ansi.Color.BLUE).a("============================================================").reset()
        )
        println(Ansi.ansi().fg(Ansi.Color.BLUE).a("           -close   - close editor").reset())
        println(Ansi.ansi().fg(Ansi.Color.BLUE).a("           -save    - save the code to the file INDEX.lzr ").reset())
        println(
            Ansi.ansi().fg(Ansi.Color.BLUE).a("============================================================").reset()
        )
        val code = StringBuilder()

        while (true) {
            print(Ansi.ansi().fg(Ansi.Color.BLUE).a("$lineNumber ~ ").reset())
            var line = scanner.nextLine()

            if ("-close" == line) {
                try {
                    val writer = FileWriter("INDEX.lzr")
                    writer.write(code.toString())
                    writer.close()
                    println("code saved to INDEX.lzr")
                } catch (e: IOException) {
                    println("IOException")
                }
                print("\u001b[H\u001b[2J")
                System.out.flush()
                break
            } else if ("-save" == line) {
                try {
                    val writer = FileWriter("INDEX.lzr")
                    writer.write(code.toString())
                    writer.close()
                    println("code saved to INDEX.lzr")
                } catch (e: IOException) {
                    println("IOException")
                }
            } else {
                code.append(line)
                code.append('\n')

                line = line.replace(
                    "\\b(if|else|print|println|while|class|new|for)\\b".toRegex(),
                    Ansi.ansi().fg(Ansi.Color.RED).a("$1").reset().toString()
                )
                line = line.replace(
                    "\\b(return|func)\\b".toRegex(),
                    Ansi.ansi().fg(Ansi.Color.BLUE).a("$1").reset().toString()
                )
                line = line.replace("//.*".toRegex(), Ansi.ansi().fg(Ansi.Color.GREEN).a("$0").reset().toString())

                println(line)

                lineNumber++
            }
        }
    }
}