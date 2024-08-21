package com.kingmang.lazurite.runner.command


enum class RunnerCommand(
    val full: String,
    val compact: String?,
    val info: String,
    val commandFull: String = "--$full",
) {
    Run(
        full = "run",
        compact = "-r",
        info = "asks for the path to the file and runs it",
    ),

    Version(
        full = "version",
        compact = "-v",
        info = "returns the version of Lazurite",
    ),

    Help(
        full = "help",
        compact = "-h",
        info = "show help commands",
    ),
    Compile(
        full = "compile_console",
        compact = "-cc",
        info = "open compiler console",
    ),

    Editor(
        full = "editor",
        compact = "-e",
        info = "open code editor",
    ),

    New(
        full = "new",
        compact = "-n",
        info = "creates new project",
    ),

    Clear(
        full = "cls",
        compact = null,
        info = "clears the command line",
    ),

    Exit(
        full = "exit",
        compact = null,
        info = "exit program",
    )
}

