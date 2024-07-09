package com.kingmang.lazurite.utils;

import com.kingmang.lazurite.console.Console;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public final class Loader {

    public static String readSource(String name) throws IOException {
        InputStream is = Loader.class.getResourceAsStream("/" + name);
        if (is != null) return readAndCloseStream(is);

        is = new FileInputStream(name);
        return readAndCloseStream(is);
    }
    
    public static String readAndCloseStream(InputStream is) throws IOException {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        final int bufferSize = 1024;
        final byte[] buffer = new byte[bufferSize];
        int read;
        while ((read = is.read(buffer)) != -1) {
            result.write(buffer, 0, read);
        }
        is.close();
        return result.toString(StandardCharsets.UTF_8);
    }
    public String loadProgram(String packageName) {
        StringBuilder content = new StringBuilder();
        InputStream inputStream = getClass().getResourceAsStream("/" + packageName + ".txt");

        if (inputStream == null) {
            Console.println("File not found in package: " + packageName);
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
