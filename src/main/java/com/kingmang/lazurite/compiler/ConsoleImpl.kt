package com.kingmang.lazurite.compiler

import ru.DmN.pht.jvm.console.JvmCommands
import ru.DmN.pht.utils.Platforms
import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.BaseConsole
import ru.DmN.siberia.console.BuildCommands

object ConsoleImpl : BaseConsole() {
    @JvmStatic
    fun runConsole(args: Array<String>) {
        run(args)
    }

    init {
        commands += BaseCommands.ALL_COMMANDS
        commands += BuildCommands.ALL_COMMANDS
        commands += JvmCommands.ALL_COMMANDS

        Platforms
    }
}