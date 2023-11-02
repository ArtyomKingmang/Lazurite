package com.kingmang.lazurite.LZREx;

public class LZRException extends RuntimeException {
    private final String type;
    private final String text;

    public LZRException(String type, String text) {
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