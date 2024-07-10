package me.besstrunovpw.lazurite.crashhandler.reporter.output.impl

import me.besstrunovpw.lazurite.crashhandler.reporter.output.IReportOutput

class ConsoleReportOutput : IReportOutput {

    override fun out(report: String) {
        println(report)
    }

}
