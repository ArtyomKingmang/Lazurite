package com.kingmang.lazurite.libraries.lzrx.awt.lxml;

import com.kingmang.lazurite.runtime.values.LzrValue;
import org.dom4j.Node;
import org.jetbrains.annotations.NotNull;

public class LzrXmlNodeValue implements LzrValue {
    Node node;

    public LzrXmlNodeValue(Node node) {
        this.node = node;
    }

    @Override
    public Object raw() {
        return node;
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
        return node.toString();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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