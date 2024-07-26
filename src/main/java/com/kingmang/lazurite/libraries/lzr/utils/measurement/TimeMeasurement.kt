package com.kingmang.lazurite.libraries.lzr.utils.measurement

import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

class TimeMeasurement : Serializable {
    private val finished: MutableMap<String, Long> = HashMap()
    private val running: MutableMap<String, Long> = HashMap()

    fun clear() {
        finished.clear()
        running.clear()
    }

    fun start(vararg names: String) {
        val time = System.nanoTime()
        for (name in names) {
            running[name] = time
        }
    }

    fun pause(vararg names: String) {
        val time = System.nanoTime()
        for (name in names) {
            if (running.containsKey(name)) {
                addTime(name, time - running[name]!!)
                running.remove(name)
            }
        }
    }

    fun stop(vararg names: String) {
        val time = System.nanoTime()
        for (name in names) {
            if (running.containsKey(name)) {
                addTime(name, time - running[name]!!)
            }
        }
    }

    fun getFinished(): Map<String, Long> {
        return finished
    }

    @JvmOverloads
    fun summary(unit: TimeUnit = TimeUnit.SECONDS, showSummary: Boolean = true): String {
        val unitName = unit.name.lowercase(Locale.getDefault())
        val result = StringBuilder()
        var summaryTime: Long = 0
        for ((key, value) in finished) {
            val convertedTime = unit.convert(value, TimeUnit.NANOSECONDS)
            summaryTime += convertedTime

            result.append(key).append(": ")
                .append(convertedTime).append(' ').append(unitName)
                .append("\n")
        }
        if (showSummary) {
            result.append("Summary: ")
                .append(summaryTime).append(' ').append(unitName)
                .append("\n")
        }
        return result.toString()
    }

    private fun addTime(name: String, time: Long) {
        val alreadyElapsed = finished.getOrDefault(name, 0L)
        finished[name] = alreadyElapsed + time
    }
}