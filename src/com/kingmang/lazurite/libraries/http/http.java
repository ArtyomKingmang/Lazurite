package com.kingmang.lazurite.libraries.http;

import com.kingmang.lazurite.LZREx.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import com.kingmang.lazurite.parser.pars.Console;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


public final class http implements Library {

    public static void initConstants() {}

    @Override
    public void init() {
        LZRMap http = new LZRMap(3);
        LZRMap url = new LZRMap(1);
        initConstants();
        url.set("encode", new URLEncode());
        http.set("request", new Http());
        http.set("getContentLength", this::HTTPGetContentLength);
        http.set("download", this::HTTPDownload);
        Variables.define("http", http);
        Variables.define("url", url);
    }

    private Value HTTPGetContentLength(Value... args) {
        Arguments.check(1, args.length);
        return LZRNumber.of(getContentLength(args[0].asString()));
    }

    public final class URLEncode implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkOrOr(1, 2, args.length);

            String charset = "UTF-8";
            if (args.length >= 2) {
                charset = args[1].asString();
            }

            try {
                final String result = URLEncoder.encode(args[0].asString(), charset);
                return new LZRString(result);
            } catch (IOException ex) {
                return args[0];
            }
        }
    }
    private Value HTTPDownload(Value... args) {
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
    public final class Http implements Function {

        private final Value
                HEADER_KEY = new LZRString("header"),
                CHARSET_KEY = new LZRString("charset"),
                ENCODED_KEY = new LZRString("encoded"),
                CONTENT_TYPE = new LZRString("content_type"),
                EXTENDED_RESULT = new LZRString("extended_result");

        private final MediaType URLENCODED_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");

        private final OkHttpClient client = new OkHttpClient();

        @Override
        public Value execute(Value... args) {
            String url, method;
            switch (args.length) {
                case 1: // http(url)
                    url = args[0].asString();
                    return process(url, "GET");

                case 2: // http(url, method) || http(url, callback)
                    url = args[0].asString();
                    if (args[1].type() == Types.FUNCTION) {
                        return process(url, "GET", (LZRFunction) args[1]);
                    }
                    return process(url, args[1].asString());

                case 3: // http(url, method, params) || http(url, method, callback)
                    url = args[0].asString();
                    method = args[1].asString();
                    if (args[2].type() == Types.FUNCTION) {
                        return process(url, method, (LZRFunction) args[2]);
                    }
                    return process(url, method, args[2], LZRFunction.EMPTY);

                case 4: // http(url, method, params, callback)
                    if (args[3].type() != Types.FUNCTION) {
                        throw new LZRException("TypeExeption ","Fourth arg must be a function callback");
                    }
                    url = args[0].asString();
                    method = args[1].asString();
                    return process(url, method, args[2], (LZRFunction) args[3]);

                case 5: // http(url, method, params, headerParams, callback)
                    if (args[3].type() != Types.MAP) {
                        throw new LZRException("TypeExeption ","Third arg must be a map");
                    }
                    if (args[4].type() != Types.FUNCTION) {
                        throw new LZRException("TypeExeption ","Fifth arg must be a function callback");
                    }
                    url = args[0].asString();
                    method = args[1].asString();
                    return process(url, method, args[2], (LZRMap) args[3], (LZRFunction) args[4]);

                default:
                    throw new LZRException("ArgumentsMismatchException ", "From 1 to 5 arguments expected, got " + args.length);
            }
        }

        private Value process(String url, String method) {
            return process(url, method, LZRFunction.EMPTY);
        }

        private Value process(String url, String method, LZRFunction function) {
            return process(url, method, LZRMap.EMPTY, function);
        }

        private Value process(String url, String method, Value params, LZRFunction function) {
            return process(url, method, params, LZRMap.EMPTY, function);
        }

        private Value process(String url, String methodStr, Value requestParams, LZRMap options, LZRFunction function) {
            final String method = methodStr.toUpperCase();
            final Function callback = function.getValue();
            try {
                final Request.Builder builder = new Request.Builder()
                        .url(url)
                        .method(method, getRequestBody(method, requestParams, options));
                if (options.containsKey(HEADER_KEY)) {
                    applyHeaderParams((LZRMap) options.get(HEADER_KEY), builder);
                }

                final Response response = client.newCall(builder.build()).execute();
                callback.execute(getResult(response, options));
                return LZRNumber.fromBoolean(response.isSuccessful());
            } catch (IOException ex) {
                return LZRNumber.fromBoolean(false);
            }
        }

        private Value getResult(Response response, LZRMap options) throws IOException {
            if (options.containsKey(EXTENDED_RESULT)) {
                final LZRMap map = new LZRMap(10);
                map.set("text", new LZRString(response.body().string()));
                map.set("message", new LZRString(response.message()));
                map.set("code", LZRNumber.of(response.code()));
                final LZRMap headers = new LZRMap(response.headers().size());
                for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
                    final int valuesSize = entry.getValue().size();
                    final LZRArray values = new LZRArray(valuesSize);
                    for (int i = 0; i < valuesSize; i++) {
                        values.set(i, new LZRString(entry.getValue().get(i)));
                    }
                    headers.set(entry.getKey(), values);
                }
                map.set("headers", headers);
                map.set("content_length", LZRNumber.of(response.body().contentLength()));
                map.set(CONTENT_TYPE, new LZRString(response.body().contentType().toString()));
                return map;
            }
            return new LZRString(response.body().string());
        }

        private void applyHeaderParams(LZRMap headerParams, Request.Builder builder) {
            for (Map.Entry<Value, Value> p : headerParams) {
                builder.header(p.getKey().asString(), p.getValue().asString());
            }
        }

        private RequestBody getRequestBody(String method, Value params, LZRMap options) throws UnsupportedEncodingException {
            if (!HttpMethod.permitsRequestBody(method)) return null;

            if (params.type() == Types.MAP) {
                return getMapRequestBody((LZRMap) params, options);
            }
            return getStringRequestBody(params, options);
        }

        private RequestBody getMapRequestBody(LZRMap params, LZRMap options) {
            final FormBody.Builder form = new FormBody.Builder();
            final boolean alreadyEncoded = (options.containsKey(ENCODED_KEY)
                    && options.get(ENCODED_KEY).asInt() != 0);
            for (Map.Entry<Value, Value> param : params) {
                final String name = param.getKey().asString();
                final String value = param.getValue().asString();
                if (alreadyEncoded)
                    form.addEncoded(name, value);
                else
                    form.add(name, value);
            }
            return form.build();
        }

        private RequestBody getStringRequestBody(Value params, LZRMap options) throws UnsupportedEncodingException {
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
