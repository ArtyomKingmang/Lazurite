package com.kingmang.lazurite.exceptions.parser;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.exceptions.parser.ParseError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ParseErrors implements Iterable<ParseError> {

    private final List<ParseError> errors;

    public ParseErrors() {
        errors = new ArrayList<>();
    }
    
    public void clear() {
        errors.clear();
    }
    
    public void add(Exception ex, int line) {
        errors.add(new ParseError(line, ex));
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public Iterator<ParseError> iterator() {
        return errors.iterator();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (ParseError error : errors) {
            result.append(error).append(Console.newline());
        }
        return result.toString();
    }
}
