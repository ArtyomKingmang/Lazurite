package com.kingmang.lazurite.libraries.HTTP;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public final class HTTP implements Library {

    public static void initConstants() {
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("URLEncode", new URLEncode());
        KEYWORD.put("HTTP", new http_http());
        KEYWORD.put("getContentLength", this::getContentLength);
        KEYWORD.put("downloader", this::downloader);
    }

    private Value getContentLength(Value... args) {
        Arguments.check(1, args.length);
        return LZRNumber.of(getContentLength(args[0].asString()));
    }

    private Value downloader(Value... args) {
        Arguments.checkRange(2, 4, args.length);
        final String downloadUrl = args[0].asString();
        final String filePath = args[1].asString();
        final Function progressCallback;
        final int contentLength;
        if ( (args.length >= 3) && (args[2].type() == Types.FUNCTION) ) {
            progressCallback = ((LZRFunction) args[2]).getValue();
            // For showing progress we need to get content length
            contentLength = getContentLength(downloadUrl);
        } else {
            progressCallback = null;
            contentLength = -1;
        }
        final int bufferSize = (args.length == 4) ? Math.max(1024, args[3].asInt()) : 16384;

        final LZRNumber contentLengthBoxed = LZRNumber.of(contentLength);
        final boolean calculateProgressEnabled = contentLength > 0 && progressCallback != null;
        if (calculateProgressEnabled) {
            progressCallback.execute(
                    LZRNumber.ZERO /*%*/,
                    LZRNumber.ZERO /*bytes downloaded*/,
                    contentLengthBoxed /*file size*/);
        }

        try (InputStream is = new URL(downloadUrl).openStream();
             OutputStream os = new FileOutputStream(Console.fileInstance(filePath))) {
            int downloaded = 0;
            final byte[] buffer = new byte[bufferSize];
            int read;
            while ((read = is.read(buffer, 0, bufferSize)) != -1) {
                os.write(buffer, 0, read);
                downloaded += read;
                if (calculateProgressEnabled) {
                    final int percent = (int) (downloaded / ((double) contentLength) * 100.0);
                    progressCallback.execute(
                            LZRNumber.of(percent),
                            LZRNumber.of(downloaded),
                            contentLengthBoxed);
                }
            }
        } catch (IOException ioe) {
            return LZRNumber.ZERO;
        } finally {
            if (progressCallback != null) {
                progressCallback.execute(LZRNumber.of(100), contentLengthBoxed, contentLengthBoxed);
            }
        }
        return LZRNumber.ONE;
    }

    private static int getContentLength(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return connection.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
