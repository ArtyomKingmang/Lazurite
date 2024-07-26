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
            } else if (line.trim().startsWith("#jInclude")) {
                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    String[] partsOfPkg = parts[1].split("\\.");
                    final String template = "%s = JClass(%s)";
                    processedCode.append("using \"lzr.lang.reflection\"; ");
                    processedCode.append(String.format(template, partsOfPkg[2].replaceAll("\"", ""), parts[1]));
                    processedCode.append("\n");
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