package com.kingmang.lazurite.modules.HTTP;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.base.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.*;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

public final class http_http implements Function {
    
    private static final Value
            HEADER_KEY = new LZRString("header"),
            CHARSET_KEY = new LZRString("charset"),
            ENCODED_KEY = new LZRString("encoded"),
            CONTENT_TYPE = new LZRString("content_type"),
            EXTENDED_RESULT = new LZRString("extended_result");
    
    private static final MediaType URLENCODED_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    
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
                    throw new LZRExeption("TypeExeption ","Fourth arg must be a function callback");
                }
                url = args[0].asString();
                method = args[1].asString();
                return process(url, method, args[2], (LZRFunction) args[3]);
                
            case 5: // http(url, method, params, headerParams, callback)
                if (args[3].type() != Types.MAP) {
                    throw new LZRExeption("TypeExeption ","Third arg must be a map");
                }
                if (args[4].type() != Types.FUNCTION) {
                    throw new LZRExeption("TypeExeption ","Fifth arg must be a function callback");
                }
                url = args[0].asString();
                method = args[1].asString();
                return process(url, method, args[2], (LZRMap) args[3], (LZRFunction) args[4]);
                
            default:
                throw new LZRExeption("ArgumentsMismatchException ", "From 1 to 5 arguments expected, got " + args.length);
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
