package com.kingmang.lazurite.libraries.jsn;

import com.kingmang.lazurite.LZREx.LzrExeption;
import com.kingmang.lazurite.base.Arguments;

import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.ClassValue;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jsn implements Module {
    private static Value execute(Value... args) {
        Arguments.check(2, args.length);
        String retstr = null;
        try {
            Object obj = new JSONParser().parse(args[0].raw().toString());
            JSONObject jo = (JSONObject) obj;
            retstr = jo.get(args[1].raw().toString()).toString();
        } catch (ParseException ex) {
            throw new LzrExeption("JsonParseError", "cannot parse json file");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new StringValue(retstr);
    }

    public void init() {
        Map<String, Value> json = new HashMap<>();
        json.put("get", new FunctionValue((jsn::execute)));
        json.put("write", new FunctionValue((args -> {
            Arguments.check(2, args.length);
            JSONObject obj = new JSONObject();
            Value[] v = ((ArrayValue) args[0]).array();

            for(Value i : v){
                String[] str = i.raw().toString().split(":");
                try {
                    obj.put(str[0], str[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter file = new FileWriter(args[1].raw().toString())) {
                //file.write(obj.toJSONString());
            } catch (IOException e) {
                throw new LzrExeption("FileWriteError", "can't write json object to file");
            }
            return NumberValue.MINUS_ONE;
        })));
        newClass("json", new ArrayList<>(), json);
    }
    private static void newClass(String name, List<String> structArgs, Map<String, Value> targets) {
        ClassValue result = new ClassValue(name, structArgs);
        for (Map.Entry<String, Value> entry : targets.entrySet()) {
            Value expr = entry.getValue();
            result.setField(entry.getKey(), expr);
        }

        Variables.set(name, result);
    }
}