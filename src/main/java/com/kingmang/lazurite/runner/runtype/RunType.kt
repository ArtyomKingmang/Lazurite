package com.kingmang.lazurite.runner.runtype

sealed class RunType {

    data class File(val runPath: String) : RunType()

    data class Project(val runPath: String, val runFile: String) : RunType()
}