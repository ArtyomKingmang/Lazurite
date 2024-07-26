package com.kingmang.lazurite.libraries.lzr.utils.measurement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeMeasurement implements Serializable {
    private final Map<String, Long> finished, running;

    public TimeMeasurement() {
        finished = new HashMap<>();
        running = new HashMap<>();
    }

    public void clear() {
        finished.clear();
        running.clear();
    }

    public void start(String... names) {
        final long time = System.nanoTime();
        for (String name : names) {
            running.put(name, time);
        }
    }

    public void pause(String... names) {
        final long time = System.nanoTime();
        for (String name : names) {
            if (running.containsKey(name)) {
                addTime(name, time - running.get(name));
                running.remove(name);
            }
        }
    }

    public void stop(String... names) {
        final long time = System.nanoTime();
        for (String name : names) {
            if (running.containsKey(name)) {
                addTime(name, time - running.get(name));
            }
        }
    }

    public Map<String, Long> getFinished() {
        return finished;
    }

    public String summary() {
        return summary(TimeUnit.SECONDS, true);
    }

    public String summary(TimeUnit unit, boolean showSummary) {
        final String unitName = unit.name().toLowerCase();
        final StringBuilder result = new StringBuilder();
        long summaryTime = 0;
        for (Map.Entry<String, Long> entry : finished.entrySet()) {
            final long convertedTime = unit.convert(entry.getValue(), TimeUnit.NANOSECONDS);
            summaryTime += convertedTime;

            result.append(entry.getKey()).append(": ")
                    .append(convertedTime).append(' ').append(unitName)
                    .append("\n");
        }
        if (showSummary) {
            result.append("Summary: ")
                    .append(summaryTime).append(' ').append(unitName)
                    .append("\n");
        }
        return result.toString();
    }

    private void addTime(String name, long time) {
        final long alreadyElapsed = finished.getOrDefault(name, 0L);
        finished.put(name, alreadyElapsed + time);
    }
}