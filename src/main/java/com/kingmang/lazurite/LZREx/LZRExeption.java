package com.kingmang.lazurite.LZREx;

public class LZRExeption extends RuntimeException {
    private final String type;
    private final String text;

    public LZRExeption(String type, String text) {
        super();
        this.type = type;
        this.text = "\u001b[31m" + text;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}