package com.kingmang.lazurite.exceptions;

import lombok.Getter;

public class LzrException extends RuntimeException {
    @Getter
    private final String type;
    @Getter
    private final String text;

    public LzrException(String type, String text) {
        super();
        //PrettyException.message(true,type, text);
        this.type = type;
        this.text = text;

    }

}