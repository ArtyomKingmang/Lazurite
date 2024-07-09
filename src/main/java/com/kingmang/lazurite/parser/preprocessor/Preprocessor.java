package com.kingmang.lazurite.parser.preprocessor;

import com.kingmang.lazurite.runner.RunnerInfo;

import java.util.HashMap;
import java.util.Map;

public class Preprocessor {

    public static String preprocess(String code) {
        Map<String, String> macros = new HashMap<>();
        macros.put("lzrVersion", "\"" + RunnerInfo.VERSION + "\"");

        StringBuilder processedCode = new StringBuilder();

        String[] lines = code.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("#define")) {
                String[] parts = line.trim().split("\\s+", 3);
                if (parts.length == 3) {
                    macros.put(parts[1], parts[2]);
                }
            } else if (line.trim().startsWith("#include")) {
                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    processedCode.append("using ").append(parts[1]).append("\n");
                }
            } else {
                for (Map.Entry<String, String> entry : macros.entrySet()) {
                    line = line.replace(entry.getKey(), entry.getValue());
                }
                processedCode.append(line).append("\n");
            }
        }

        return processedCode.toString();
    }


}
