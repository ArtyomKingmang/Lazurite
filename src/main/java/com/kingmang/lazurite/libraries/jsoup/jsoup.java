package com.kingmang.lazurite.libraries.jsoup;

import com.annimon.ownlang.lib.MapValue;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class jsoup implements Library {

    public static String docs = "";
    public static Document docum;
    public static Elements element;


    @Override
    public void init() {
        KEYWORD.put("pars", new pars());
        KEYWORD.put("select", new select());

    }

    private static class pars implements Function {
        @Override
        public Value execute(Value... args) {
            Document doc = null;
            try {
                doc = Jsoup.connect(args[0].toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            docs = doc.toString();
            Variables.set("document", new LZRString((docs).getBytes(StandardCharsets.UTF_8).toString()));
            docum = doc;
            return new LZRString(docum.toString());
        }
    }


    private static class select implements Function {
        @Override
        public Value execute(Value... args) {
            Elements divs = docum.select(args[0].toString());
            Variables.set("elements", new LZRString((divs.toString()).getBytes(StandardCharsets.UTF_8).toString()));
            element = divs;
            return new LZRString(element.toString());
        }
    }

}
