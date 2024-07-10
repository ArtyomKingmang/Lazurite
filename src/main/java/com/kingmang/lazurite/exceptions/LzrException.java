package com.kingmang.lazurite.exceptions;

import lombok.Getter;

@Getter
public class LzrException extends RuntimeException {
    private final String type;
    private final String text;

    public LzrException(String type, String text) {
        super();
        //PrettyException.message(true,type, text);
        this.type = type;
        this.text = text;

    }

}