package com.kingmang.lazurite.libraries.processingApi;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.Variables;


public class processingApi implements Library {
    @Override
    public void init() {
        /*LZRMap processing = new LZRMap(4);
        processing.set("run",new Sketch.run());*/
        Keyword.put("size", new Sketch.run());
        //processing.set("setup", new Sketch.setup());
        //Variables.define("processing", processing);
    }
}
