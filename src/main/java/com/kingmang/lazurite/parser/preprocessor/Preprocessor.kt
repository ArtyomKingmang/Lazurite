package com.kingmang.lazurite.parser.preprocessor;

import com.kingmang.lazurite.runner.RunnerInfo;
import com.kingmang.lazurite.utils.Handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preprocessor {
    public static String preprocess(String code) {
        List<String> lines = Arrays.asList(code.split("\n"));
        Map<String, String> macros = new HashMap<>();

        macros.put("Lazurite", "\"" + RunnerInfo.getVersion() + "\"");

        StringBuilder codeBuilder = new StringBuilder(code);
        for (String line : lines) {
            List<String> parts = Arrays.asList(line.split(" "));
            if (parts.isEmpty()) continue;

            String command = parts.get(0);
            switch (command) {
                case "#define":
                    String id = parts.get(1);
                    String replacement = String.join(" ", parts.subList(2, parts.size()));
                    if (id.contains("(")) {
                        codeBuilder.insert(0, "\n" + "func " + id + " return " + replacement + "\n");
                        continue;
                    }
                    macros.put(id, replacement);
                    break;

                case "#ifdef":
                    String idIfdef = parts.get(1);
                    String replacementIfdef = String.join(" ", parts.subList(2, parts.size()));
                    if (macros.containsKey(idIfdef)) Handler.handle(replacementIfdef, "<preprocessor>", true, false);
                    break;

                case "#ifndef":
                    String idIfndef = parts.get(1);
                    String replacementIfndef = String.join(" ", parts.subList(2, parts.size()));
                    if (!macros.containsKey(idIfndef)) Handler.handle(replacementIfndef, "<preprocessor>", true, false);
                    break;

                default:
                    break;
            }
        }
        code = codeBuilder.toString();

        lines = Arrays.asList(code.split("\n"));
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String buffer = line;
            List<String> parts = Arrays.asList(line.split(" "));
            if (parts.get(0).equals("#undef")) {
                String idUndef = parts.get(1);
                macros.remove(idUndef);
                continue;
            }

            for (Map.Entry<String, String> entry : macros.entrySet()) {
                buffer = buffer.replaceAll(";", " ;");
                buffer = buffer.replaceAll(String.format("(?<!\")(%s)(?!\")", entry.getKey()), entry.getValue()) + "\n";
            }
            result.append(buffer);
        }

        result = new StringBuilder(result.toString()
                .replace("#define", "# PROCESSED DEFINE MACROS")
                .replace("#ifdef", "# PROCESSED IFDEF MACROS")
                .replace("#ifndef", "# PROCESSED IFNDEF MACRO")
                .replace("#undef", "# PROCESSED UNDEF MACRO"));

        return result.toString();
    }
}