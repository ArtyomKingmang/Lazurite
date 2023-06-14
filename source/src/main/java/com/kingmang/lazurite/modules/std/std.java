package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.lib.Functions;


public final class std implements Module {


    @Override
    public void init() {


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
