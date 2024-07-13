package com.kingmang.lazurite.runner.command

object RunnerCommandHandler {

    fun handleArgs(args: List<String>): RunnerCommandData? {
        if (args.isEmpty()) return null
        val command = args[0].findCommandByArg() ?: return null
        val commandArgs = args.excludeCommandArg()
        return RunnerCommandData(command, commandArgs)
    }

    fun readInput(): RunnerCommandData? {
        val args = readln().split(' ').filter { it.isNotEmpty() }
        return handleArgs(args)
    }

    private fun String.findCommandByArg(): RunnerCommand? {
        return RunnerCommand.entries.find {
            this == it.full || this == it.commandFull || this == it.compact
        }
    }

    private fun List<String>.excludeCommandArg(): List<String> {
        return slice(1 until size)
    }
}
