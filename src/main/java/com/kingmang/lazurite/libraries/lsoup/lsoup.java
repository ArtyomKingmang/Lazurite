package com.kingmang.lazurite.libraries.lsoup;


import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;
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
        LzrMap lsoup = new LzrMap(3);
        lsoup.set("parse", new pars());
        lsoup.set("select", new select());
        lsoup.set("body", new body());
        Variables.define("lsoup", lsoup);
    }

    private static class body implements Function {
        @Override
        public LzrValue execute(LzrValue... args) {
            if (docum != null) {
                return new LzrString(docum.body().toString());
            } else {
                return new LzrString("Document not parsed yet");
            }
        }
    }

    private static class pars implements Function {
        @Override
        public LzrValue execute(LzrValue... args) {
            Document doc = null;
            try {
                doc = Jsoup.connect(args[0].toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            docs = doc.toString();
            Variables.set("document", new LzrString((docs).getBytes(StandardCharsets.UTF_8).toString()));
            docum = doc;
            return new LzrString(docum.toString());


        }
    }


    private static class select implements Function {
        @Override
        public LzrValue execute(LzrValue... args) {
            Elements divs = docum.select(args[0].toString());
            Variables.set("elements", new LzrString((divs.toString()).getBytes(StandardCharsets.UTF_8).toString()));
            element = divs;
            return new LzrString(element.toString());
        }
    }

}
