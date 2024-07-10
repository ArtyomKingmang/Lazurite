package com.kingmang.lazurite.libraries.lzr.jloader;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings({"unused", "ClassName"})
public class jloader implements Library {


    @Override
    public void init() {
        LzrMap base = new LzrMap(1);
        base.set("invoke", args -> {
            Arguments.check(5, args.length);
            final String cname = args[2].asString();
            final String class_ = args[1].asString();
            final int isStatic = args[4].asInt();
            try {
                File file = new File(args[0].asString());
                URLClassLoader c = new URLClassLoader(new URL[]{file.toURI().toURL()});

                Class<?> LClass = c.loadClass(class_);
                Object instance = LClass.newInstance();
                Method method;
                if(isStatic == 1){
                    method = LClass.getDeclaredMethod(cname, Object[].class);
                }else{
                    method = LClass.getDeclaredMethod(cname, String[].class);
                }


                // Invoke the method on the class itself

                if(!args[3].toString().isEmpty()){
                    method.invoke(null, (Object) new Object[] {args[3]});
                }else{
                    method.invoke(null);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return LzrNumber.ZERO;
        });

        Variables.define("jloader", base);
    }
}
