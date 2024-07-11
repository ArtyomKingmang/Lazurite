package com.kingmang.lazurite.crashHandler.reporter.output.impl

import com.kingmang.lazurite.crashHandler.reporter.output.IReportOutput

class ConsoleReportOutput : IReportOutput {

    override fun out(report: String) {
        println(report)
    }

}
