package com.kingmang.lazurite.modules.Colors;


import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.ClassValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Colors implements Module {

    private static void initColor() {
        Map<String, Value> color = new HashMap<>();
        Map<String, Value> backcolor = new HashMap<>();
        color.put("reset", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[0m" + args[0]);
        })));
        color.put("black", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[30m" + args[0]);
        })));
        color.put("red", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[31m" + args[0]);
        })));
        color.put("green", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[32m" + args[0]);
        })));
        color.put("yellow", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[33m" + args[0]);
        })));
        color.put("blue", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[34m" + args[0]);
        })));
        color.put("purple", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[35m" + args[0]);
        })));
        color.put("cyan", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[36m" + args[0]);
        })));
        color.put("white", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[37m" + args[0]);
        })));
        backcolor.put("reset", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[10m" + args[0]);
        })));
        backcolor.put("black", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[40m" + args[0]);
        })));
        backcolor.put("red", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[41m" + args[0]);
        })));
        backcolor.put("green", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[42m" + args[0]);
        })));
        backcolor.put("yellow", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[43m" + args[0]);
        })));
        backcolor.put("blue", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[44m" + args[0]);
        })));
        backcolor.put("purple", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[45m" + args[0]);
        })));
        backcolor.put("cyan", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[46m" + args[0]);
        })));
        backcolor.put("white", new FunctionValue((args -> {
            Arguments.check(1, args.length);
            return new StringValue("\u001b[47m" + args[0]);
        })));
        newClass("color", new ArrayList<>(), color);
        newClass("backcolor", new ArrayList<>(), backcolor);
    }

    public void initConstant(){
        Variables.set("RED", new NumberValue(16711688));
        Variables.set("GREEN", new NumberValue(65309));
        Variables.set("BLUE", new NumberValue(5887));
        Variables.set("WHITE", new NumberValue(16777215));
        Variables.set("BLACK", new NumberValue(0));
        Variables.set("PURPLE", new NumberValue(9109759));
        Variables.set("PINK", new NumberValue(16761037));
        Variables.set("YELLOW", new NumberValue(16776960));
    }
    public void init() {
        initConstant();
        initColor();
    }

    private static void newClass(String name, List<String> structArgs, Map<String, Value> targets) {
        ClassValue result = new ClassValue(name, structArgs) {
            @Override
            public int compareTo(@NotNull Value o) {
                return 0;
            }

            @Override
            public int type() {
                return 0;
            }
        };
        for (Map.Entry<String, Value> entry : targets.entrySet()) {
            Value expr = entry.getValue();
            result.setField(entry.getKey(), expr);
        }

        Variables.set(name, result);
    }
}