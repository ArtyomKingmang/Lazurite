package com.kingmang.lazurite.libraries.lzrx.awt.lxml;

import com.kingmang.lazurite.runtime.values.LzrValue;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jetbrains.annotations.NotNull;

public class LzrXmlElementValue implements LzrValue {

    private Element element;
    private Document doc;

    public LzrXmlElementValue(Element element, Document doc) {
        this.element = element;
        this.doc = doc;
    }

    public LzrXmlElementValue(Element element) {
        this.element = element;
    }

    @Override
    public Object raw() {
        return element;
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
        return element.getName();
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
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