package com.kingmang.lazurite.crashHandler.reporter.output.impl

import com.kingmang.lazurite.crashHandler.reporter.output.IReportOutput
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileReportOutput : IReportOutput {

    override fun out(report: String) {
        try {
            val out: Path = Paths.get("internal_lazurite_error_${System.currentTimeMillis()}.txt")
            Files.writeString(out, report)

            println("Crash report saved as: $out")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

}
