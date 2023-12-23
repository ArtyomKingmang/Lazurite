package com.kingmang.lazurite.libraries.lsoup;


import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class lsoup implements Library {

    public static String docs = "";
    public static Document docum;
    public static Elements element;
    public static Element elem;

    @Override
    public void init() {
        KEYWORD.put("SoupParse", new pars());
        KEYWORD.put("SoupSelect", new select());
        KEYWORD.put("SoupBody", new body());
    }

    private static class body implements Function {
        @Override
        public Value execute(Value... args) {
            if (docum != null) {
                return new LZRString(docum.body().toString());
            } else {
                return new LZRString("Document not parsed yet");
            }
        }
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
