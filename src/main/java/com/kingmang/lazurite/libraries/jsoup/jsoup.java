package com.kingmang.lazurite.libraries.jsoup;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Module;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class jsoup implements Module{

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
