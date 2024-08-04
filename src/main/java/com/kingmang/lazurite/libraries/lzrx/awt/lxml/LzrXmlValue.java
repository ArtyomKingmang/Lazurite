package com.kingmang.lazurite.libraries.lzrx.awt.lxml;

import com.kingmang.lazurite.runtime.values.LzrValue;
import org.dom4j.Document;
import org.jetbrains.annotations.NotNull;

public class LzrXmlValue implements LzrValue {
    private Document document;

    public LzrXmlValue(Document document) {
        this.document = document;
    }

    @Override
    public Object raw() {
        return document;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asNumber() {
        return 0;
    }

    @Override
    public String asString() {
        return document.toString();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @NotNull
    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        return 0;
    }
}