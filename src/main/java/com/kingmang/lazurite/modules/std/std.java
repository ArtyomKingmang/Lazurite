package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.lib.Types;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.runtime.Variables;


public final class std implements Module {

    public static void initConstants() {
        Variables.define("object", NumberValue.of(Types.OBJECT));
        Variables.define("num", NumberValue.of(Types.NUMBER));
        Variables.define("string", NumberValue.of(Types.STRING));
        Variables.define("array", NumberValue.of(Types.ARRAY));
        Variables.define("map", NumberValue.of(Types.MAP));
        Variables.define("function", NumberValue.of(Types.FUNCTION));
    }
    @Override
    public void init() {
        initConstants();

        Functions.set("toStr", args -> new StringValue(args[0].asString()));
        Functions.set("toNum", args -> NumberValue.of(args[0].asNumber()));

        Functions.set("toByte", args -> NumberValue.of((byte)args[0].asInt()));
        Functions.set("toShort", args -> NumberValue.of((short)args[0].asInt()));
        Functions.set("toInt", args -> NumberValue.of(args[0].asInt()));
        Functions.set("toLong", args -> NumberValue.of((long)args[0].asNumber()));
        Functions.set("toFloat", args -> NumberValue.of((float)args[0].asNumber()));
        Functions.set("toDouble", args -> NumberValue.of(args[0].asNumber()));

        Functions.set("echo", new ECHO());
         Functions.set("readln", new INPUT());
         Functions.set("length", new LEN());
         Functions.set("getBytes", STR::getBytes);
         Functions.set("sprintf", new SPRINTF());
         Functions.set("range", new RANGE());
         Functions.set("substring", new SUBSTR());

         Functions.set("parseInt", PARSE::parseInt);
         Functions.set("parseLong", PARSE::parseLong);
         Functions.set("foreach", new FOREACH());
         Functions.set("filter", new FILTER(false));




    }
}
