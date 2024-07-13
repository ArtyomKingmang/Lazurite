package com.kingmang.lazurite.runner.runtype

import com.moandjiezana.toml.Toml
import java.io.File

object RunTypeFinder {

    @Throws(RunTypeException::class)
    fun findRunType(args: List<String>): RunType {
        return try {
            val argPath = args.getOrNull(0).orEmpty()
            val pathFile = File(System.getProperty("user.dir"), argPath)
            if (pathFile.isDirectory) {
                getProjectRunType(pathFile)
            } else {
                getFileRunType(argPath)
            }
        } catch (ex: Exception) {
            if (ex is RunTypeException) {
                throw ex
            } else {
                throw RunTypeException("Unknown exception: $ex", ex)
            }
        }
    }

    private fun getFileRunType(path: String): RunType {
        return RunType.File(path)
    }

    private fun getProjectRunType(projectDir: File): RunType.Project {
        val projectFile = File(projectDir, "project.toml")
        val runFile = getProjectRunFile(projectFile)
        val runPath = File(projectDir, runFile)
        if (!runPath.exists()) {
            throw RunTypeException("There is no $runFile file in this path")
        }
        return RunType.Project(runPath.path, runFile)
    }

    private fun getProjectRunFile(projectFile: File): String {
        val settings = try {
            Toml().read(projectFile)
        } catch (ex: Exception) {
            throw RunTypeException("Can't read project.toml file", ex)
        }
        val project = settings.getTable("package")

        if (project.contains("run_file")) {
            return project.getString("run_file")
        }
        if (project.contains("lib_file")) {
            throw RunTypeException("Can't run library package")
        }
        throw RunTypeException("Can't run code without run_file")
    }
}
