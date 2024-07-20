package com.kingmang.lazurite.runner.project

import com.kingmang.lazurite.runner.RunnerInfo
import com.moandjiezana.toml.TomlWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.writeText
import kotlin.io.path.writer

object ProjectCreator {
    @Throws(ProjectCreatorException::class)
    fun create(args: List<String>) {
        try {
            val input = handleArguments(args)
            createByInput(input)
        } catch (ex: ProjectCreatorException) {
            throw ex
        } catch (ex: Exception) {
            throw ProjectCreatorException("Unknown exception: $ex", ex)
        }
    }

    private fun handleArguments(args: List<String>): Input {
        val arg0 = args.getOrNull(0)
        val arg1 = args.getOrNull(1)
        val isLib = arg0?.let { it == "--lib" || it == "-l" } ?: false
        val name = (if (isLib) arg1 else arg0) ?: throw ProjectCreatorException("Project name not specified")
        if (name.startsWith("-") || !name.matches("^[a-z0-9\\-]+$".toRegex()))
            throw ProjectCreatorException("Name of project should contain only `a-z`, `0-9` and `-`")
        return Input(name, isLib)
    }

    private fun createByInput(input: Input) {
        val projectPath = createProjectDir(input)
        val srcPath = createSrcDir(projectPath)
        createLzrLibsDir()
        createDefaultSrcFile(input, srcPath)
        createProjectFile(input, projectPath)
    }

    private fun createProjectDir(input: Input): Path {
        val projectPath = Paths.get(System.getProperty("user.dir"), input.name)
        if (Files.isDirectory(projectPath)) {
            Files.newDirectoryStream(projectPath).use {
                if (it.any()) {
                    throw ProjectCreatorException("Can't create project ${input.name} in existing folder that contains something in it")
                }
            }
        } else Files.createDirectory(projectPath)
        return projectPath
    }

    private fun createSrcDir(projectPath: Path): Path {
        val srcPath = Paths.get(projectPath.toString(), "src")
        Files.createDirectory(srcPath)
        return srcPath
    }

    private fun createLzrLibsDir(): Path {
        val lzrLibsPath = Paths.get(RunnerInfo.LZR_LIBS_PATH)
        if (!Files.exists(lzrLibsPath))
            Files.createDirectory(lzrLibsPath)
        return lzrLibsPath
    }

    private fun createDefaultSrcFile(input: Input, srcPath: Path) {
        if (input.isLibrary)
            Files.createFile(Paths.get(srcPath.toString(), "lib.lzr"))
        else Files.createFile(Paths.get(srcPath.toString(), "main.lzr")).writeText("print(\"Hello World\")")
    }

    private fun createProjectFile(input: Input, projectPath: Path) {
        val settingsMap = createSetting(input)
        Files.createFile(Paths.get(projectPath.toString(), "project.toml")).writer().use {
            TomlWriter().write(settingsMap, it)
        }
    }

    private fun createSetting(input: Input): Map<String, Any> = mapOf(
        "package" to mapOf(
            "name" to input.name,
            "version" to "0.1.0",
            if (input.isLibrary) {
                "lib_file" to "/src/lib.lzr"
            } else {
                "run_file" to "/src/main.lzr"
            }
        )
    )

    private data class Input(
        val name: String,
        val isLibrary: Boolean
    )
}
