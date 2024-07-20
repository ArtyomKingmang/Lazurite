package com.kingmang.lazurite.runner.console

import com.kingmang.lazurite.runner.RunnerInfo
import com.kingmang.lazurite.runner.command.RunnerCommand
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

object ConsoleUI {
    private val HELP_COMMANDS = listOf(
        RunnerCommand.Run,
        RunnerCommand.Version,
        RunnerCommand.Help,
        RunnerCommand.Editor,
        RunnerCommand.New,
        RunnerCommand.Clear,
    )

    private val HEADER by lazy {
        val version = "-------${RunnerInfo.VERSION}-------"
        val title = "Lazurite Console"
        val maxLength = maxOf(version.length, title.length)
        val divider = "-".repeat(maxLength)
        "\n\t$version\n\t$title\n\t$divider"
    }

    private val HELP by lazy {
        buildString {
            HELP_COMMANDS.forEach {
                append(it.commandFull)
                append(" / ")
                append(it.full)
                if (it.compact != null) {
                    append(" / ")
                    append(it.compact)
                }
                append(" - ")
                append(it.info)
                appendLine()
            }
        }
    }

    private const val DIVIDER = "------------------------------------------------------------"

    private const val COMMANDS_DIVIDER = "\n$DIVIDER\n"

    private const val VERSION_DIVIDER = "---------------------------------"

    fun printFirstHelp() {
        AnsiConsole.systemInstall()
        println(Ansi.ansi().fg(Ansi.Color.RED).a(HEADER).reset())
        AnsiConsole.out().println(DIVIDER)
        AnsiConsole.out().println(HELP)
    }

    fun printHelp() {
        println(HEADER)
        println(DIVIDER)
        println(HELP)
    }

    fun printCommandsDivider() {
        println(COMMANDS_DIVIDER)
    }

    fun printVersion() {
        println(VERSION_DIVIDER)
        println("Lazurite version: ${RunnerInfo.VERSION}")
        println(VERSION_DIVIDER)
    }

    fun clear() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }
}