package com.kingmang.lazurite.runner

import com.kingmang.lazurite.editors.Editor
import com.kingmang.lazurite.runner.command.RunnerCommand
import com.kingmang.lazurite.runner.command.RunnerCommandData
import com.kingmang.lazurite.runner.command.RunnerCommandHandler
import com.kingmang.lazurite.runner.console.ConsoleUI
import com.kingmang.lazurite.runner.project.ProjectCreator
import com.kingmang.lazurite.runner.project.ProjectCreatorException
import com.kingmang.lazurite.runner.runtype.RunType
import com.kingmang.lazurite.runner.runtype.RunTypeException
import com.kingmang.lazurite.runner.runtype.RunTypeFinder
import com.kingmang.lazurite.utils.Handler
import java.io.*


object Runner {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            console()
        } else {
            command(args)
        }
    }

    private fun command(cmd: Array<String>) {
        val commandData = RunnerCommandHandler.handleArgs(cmd.toList())
        handleCommandData(commandData)
    }

    private fun console() {
        ConsoleUI.printFirstHelp()
        do {
            ConsoleUI.printCommandsDivider()
            val commandData = RunnerCommandHandler.readInput()
        } while (handleCommandData(commandData))
    }

    private fun handleCommandData(commandData: RunnerCommandData?): Boolean {
        var optimizeLvl : Int = 0
        var printResOfOpt : Boolean = false


        if (commandData == null) {
            println("Command not found!")
            return true
        }
        if(commandData.args[1] == "-o"){
            printResOfOpt = true
        }
        optimizeLvl = commandData.args[2].toInt()

        when (commandData.command) {
            //RunnerCommand.Compile -> ConsoleUI.showCompilerConsole(commandData.args.toTypedArray());
            RunnerCommand.Compile -> ConsoleUI.showCompilerConsole()
            RunnerCommand.Version -> ConsoleUI.printVersion()
            RunnerCommand.Help -> ConsoleUI.printHelp()
            RunnerCommand.Editor -> Editor.openEditor()
            RunnerCommand.Clear -> ConsoleUI.clear()
            RunnerCommand.New -> createProject(commandData.args)
            RunnerCommand.Run -> runProgram(commandData.args, true, optimizeLvl, printResOfOpt)
            RunnerCommand.Exit -> return false
        }
        return true
    }

    private fun runProgram(args: List<String>, preprocess: Boolean, optimizeLvl : Int, printResOfOpt: Boolean) {
        val runType = try {
            RunTypeFinder.findRunType(args)
        } catch (ex: RunTypeException) {
            println(ex.message)
            return
        }

        when (runType) {
            is RunType.File -> runByFilePath(runType.runPath, runType.runPath, preprocess, optimizeLvl, printResOfOpt)
            is RunType.Project -> runByFilePath(runType.runFile, runType.runPath, preprocess, optimizeLvl, printResOfOpt)
        }
    }

    @Throws(IOException::class)
    private fun createProject(args: List<String>) {
        try {
            ProjectCreator.create(args)
        } catch (ex: ProjectCreatorException) {
            println(ex.message)
            return
        }
    }

    private fun runByFilePath(runFile: String, runPath: String, preprocess: Boolean, optimizeLvl : Int, printResOfOpt: Boolean) {
        try {
            if (preprocess) {
                Handler.run(runPath, optimizeLvl, printResOfOpt)
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Correct entry form: r <file>")
        } catch (ex: FileNotFoundException) {
            println("File $runFile not found")
        }
    }
}