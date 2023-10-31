package com.kingmang.lazurite.libraries.JS;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JS implements Library {

    static ScriptEngineManager js = new ScriptEngineManager();

    @Override
    public void init(){
        KEYWORD.put("eval", new eval());
    }

    public static class eval implements Function{

        @Override
        public Value execute(Value... args) {
            ScriptEngine engine = js.getEngineByName("js");
            Object result = null;
            try{
                result = engine.eval(args[0].asString());
            }catch (ScriptException e){
                e.printStackTrace();
            }

            return new LZRNumber((Number) result);
        }
    }

}
