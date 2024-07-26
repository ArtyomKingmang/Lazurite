package com.kingmang.lazurite.libraries.lzr.net.http;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;


public final class http implements Library {

    public static void initConstants() {}

    @Override
    public void init() {
        LzrMap http = new LzrMap(3);
        LzrMap url = new LzrMap(1);
        initConstants();

        url.set("encode", (args) -> {
            Arguments.checkOrOr(1, 2, args.length);

            String charset = "UTF-8";
            if (args.length >= 2) {
                charset = args[1].asString();
            }

            try {
                final String result = URLEncoder.encode(args[0].asString(), charset);
                return new LzrString(result);
            } catch (IOException ex) {
                return args[0];
            }
        });

        http.set("request", new Http());
        http.set("getContentLength", this::HTTPGetContentLength);
        http.set("download", this::HTTPDownload);
        Variables.define("http", http);
        Variables.define("url", url);
    }

    private LzrValue HTTPGetContentLength(LzrValue... args) {
        Arguments.check(1, args.length);
        return LzrNumber.of(getContentLength(args[0].asString()));
    }

    private LzrValue HTTPDownload(LzrValue... args) {
        Arguments.checkRange(2, 4, args.length);
        final String downloadUrl = args[0].asString();
        final String filePath = args[1].asString();
        final Function progressCallback;
        final int contentLength;
        if ( (args.length >= 3) && (args[2].type() == Types.FUNCTION) ) {
            progressCallback = ((LzrFunction) args[2]).getValue();
            // For showing progress we need to get content length
            contentLength = getContentLength(downloadUrl);
        } else {
            progressCallback = null;
            contentLength = -1;
        }
        final int bufferSize = (args.length == 4) ? Math.max(1024, args[3].asInt()) : 16384;

        final LzrNumber contentLengthBoxed = LzrNumber.of(contentLength);
        final boolean calculateProgressEnabled = contentLength > 0 && progressCallback != null;
        if (calculateProgressEnabled) {
            progressCallback.execute(
                    LzrNumber.ZERO /*%*/,
                    LzrNumber.ZERO /*bytes downloaded*/,
                    contentLengthBoxed /*file size*/);
        }

        try (InputStream is = new URL(downloadUrl).openStream();
             OutputStream os = Files.newOutputStream(Console.fileInstance(filePath).toPath())) {
            int downloaded = 0;
            final byte[] buffer = new byte[bufferSize];
            int read;
            while ((read = is.read(buffer, 0, bufferSize)) != -1) {
                os.write(buffer, 0, read);
                downloaded += read;
                if (calculateProgressEnabled) {
                    final int percent = (int) (downloaded / ((double) contentLength) * 100.0);
                    progressCallback.execute(
                            LzrNumber.of(percent),
                            LzrNumber.of(downloaded),
                            contentLengthBoxed);
                }
            }
        } catch (IOException ioe) {
            return LzrNumber.ZERO;
        } finally {
            if (progressCallback != null) {
                progressCallback.execute(LzrNumber.of(100), contentLengthBoxed, contentLengthBoxed);
            }
        }
        return LzrNumber.ONE;
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
    public static final class Http implements Function {

        private final LzrValue
                HEADER_KEY = new LzrString("header"),
                CHARSET_KEY = new LzrString("charset"),
                ENCODED_KEY = new LzrString("encoded"),
                CONTENT_TYPE = new LzrString("content_type"),
                EXTENDED_RESULT = new LzrString("extended_result");

        private final MediaType URLENCODED_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");

        private final OkHttpClient client = new OkHttpClient();

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            String url, method;
            switch (args.length) {
                case 1: // http(url)
                    url = args[0].asString();
                    return process(url, "GET");

                case 2: // http(url, method) || http(url, callback)
                    url = args[0].asString();
                    if (args[1].type() == Types.FUNCTION) {
                        return process(url, "GET", (LzrFunction) args[1]);
                    }
                    return process(url, args[1].asString());

                case 3: // http(url, method, params) || http(url, method, callback)
                    url = args[0].asString();
                    method = args[1].asString();
                    if (args[2].type() == Types.FUNCTION) {
                        return process(url, method, (LzrFunction) args[2]);
                    }
                    return process(url, method, args[2], LzrFunction.EMPTY);

                case 4: // http(url, method, params, callback)
                    if (args[3].type() != Types.FUNCTION) {
                        throw new LzrException("TypeExeption ","Fourth arg must be a function callback");
                    }
                    url = args[0].asString();
                    method = args[1].asString();
                    return process(url, method, args[2], (LzrFunction) args[3]);

                case 5: // http(url, method, params, headerParams, callback)
                    if (args[3].type() != Types.MAP) {
                        throw new LzrException("TypeExeption ","Third arg must be a map");
                    }
                    if (args[4].type() != Types.FUNCTION) {
                        throw new LzrException("TypeExeption ","Fifth arg must be a function callback");
                    }
                    url = args[0].asString();
                    method = args[1].asString();
                    return process(url, method, args[2], (LzrMap) args[3], (LzrFunction) args[4]);

                default:
                    throw new LzrException("ArgumentsMismatchException ", "From 1 to 5 arguments expected, got " + args.length);
            }
        }

        private LzrValue process(String url, String method) {
            return process(url, method, LzrFunction.EMPTY);
        }

        private LzrValue process(String url, String method, LzrFunction function) {
            return process(url, method, LzrMap.EMPTY, function);
        }

        private LzrValue process(String url, String method, LzrValue params, LzrFunction function) {
            return process(url, method, params, LzrMap.EMPTY, function);
        }

        private LzrValue process(String url, String methodStr, LzrValue requestParams, LzrMap options, LzrFunction function) {
            final String method = methodStr.toUpperCase();
            final Function callback = function.getValue();
            try {
                final Request.Builder builder = new Request.Builder()
                        .url(url)
                        .method(method, getRequestBody(method, requestParams, options));
                if (options.containsKey(HEADER_KEY)) {
                    applyHeaderParams((LzrMap) options.get(HEADER_KEY), builder);
                }

                final Response response = client.newCall(builder.build()).execute();
                callback.execute(getResult(response, options));
                return LzrNumber.fromBoolean(response.isSuccessful());
            } catch (IOException ex) {
                return LzrNumber.fromBoolean(false);
            }
        }

        private LzrValue getResult(Response response, LzrMap options) throws IOException {
            if (options.containsKey(EXTENDED_RESULT)) {
                final LzrMap map = new LzrMap(10);
                map.set("text", new LzrString(response.body().string()));
                map.set("message", new LzrString(response.message()));
                map.set("code", LzrNumber.of(response.code()));
                final LzrMap headers = new LzrMap(response.headers().size());
                for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
                    final int valuesSize = entry.getValue().size();
                    final LzrArray values = new LzrArray(valuesSize, index -> new LzrString(entry.getValue().get(index)));
                    headers.set(entry.getKey(), values);
                }
                map.set("headers", headers);
                map.set("content_length", LzrNumber.of(response.body().contentLength()));
                map.set(CONTENT_TYPE, new LzrString(response.body().contentType().toString()));
                return map;
            }
            return new LzrString(response.body().string());
        }

        private void applyHeaderParams(LzrMap headerParams, Request.Builder builder) {
            for (Map.Entry<LzrValue, LzrValue> p : headerParams) {
                builder.header(p.getKey().asString(), p.getValue().asString());
            }
        }

        private RequestBody getRequestBody(String method, LzrValue params, LzrMap options) throws UnsupportedEncodingException {
            if (!HttpMethod.permitsRequestBody(method)) return null;

            if (params.type() == Types.MAP) {
                return getMapRequestBody((LzrMap) params, options);
            }
            return getStringRequestBody(params, options);
        }

        private RequestBody getMapRequestBody(LzrMap params, LzrMap options) {
            final FormBody.Builder form = new FormBody.Builder();
            final boolean alreadyEncoded = (options.containsKey(ENCODED_KEY)
                    && options.get(ENCODED_KEY).asInt() != 0);
            for (Map.Entry<LzrValue, LzrValue> param : params) {
                final String name = param.getKey().asString();
                final String value = param.getValue().asString();
                if (alreadyEncoded)
                    form.addEncoded(name, value);
                else
                    form.add(name, value);
            }
            return form.build();
        }

        private RequestBody getStringRequestBody(LzrValue params, LzrMap options) throws UnsupportedEncodingException {
            final MediaType type;
            if (options.containsKey(CONTENT_TYPE)) {
                type = MediaType.parse(options.get(CONTENT_TYPE).asString());
            } else {
                type = URLENCODED_MEDIA_TYPE;
            }

            if (options.containsKey(CHARSET_KEY)) {
                final String charset = options.get(CHARSET_KEY).asString();
                return RequestBody.create(type, params.asString().getBytes(charset));
            }

            return RequestBody.create(type, params.asString());
        }
    }
}
