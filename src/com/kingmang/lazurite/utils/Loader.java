package com.kingmang.lazurite.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class Loader {

    private Loader() { }

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
        return result.toString("UTF-8");
    }
}
