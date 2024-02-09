package com.kingmang.lazurite.exceptions;

import lombok.Getter;

public class LZRException extends RuntimeException {
    @Getter
    private final String type;
    @Getter
    private final String text;

    public LZRException(String type, String text) {
        super();
        this.type = type;
        this.text = "\u001b[31m" + text;

    }
}