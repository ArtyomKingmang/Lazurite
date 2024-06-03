package com.kingmang.lazurite.core;

import com.kingmang.lazurite.exceptions.LzrException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Arguments {

    public static void check(int expected, int got) {
        if (got != expected) throw new LzrException("ArgumentsMismatchException ", String.format(
                "%d %s expected, got %d", expected, pluralize(expected), got));
    }

    public static void checkAtLeast(int expected, int got) {
        if (got < expected) throw new LzrException("ArgumentsMismatchException ",String.format(
                "At least %d %s expected, got %d", expected, pluralize(expected), got));
    }

    public static void checkOrOr(int expectedOne, int expectedTwo, int got) {
        if (expectedOne != got && expectedTwo != got)
            throw new LzrException("ArgumentsMismatchException ",String.format(
                    "%d or %d arguments expected, got %d", expectedOne, expectedTwo, got));
    }

    public static void checkRange(int from, int to, int got) {
        if (from > got || got > to)
            throw new LzrException("ArgumentsMismatchException ",String.format(
                    "From %d to %d arguments expected, got %d", from, to, got));
    }

    private static String pluralize(int count) {
        return (count == 1) ? "argument" : "arguments";
    }
}
