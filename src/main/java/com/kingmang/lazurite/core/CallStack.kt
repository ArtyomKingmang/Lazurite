package com.kingmang.lazurite.core;

import com.kingmang.lazurite.exceptions.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

@NoArgsConstructor
public final class CallStack {
    
    private static final Deque<CallInfo> calls = new ConcurrentLinkedDeque<>();
    
    public static synchronized void clear() {
        calls.clear();
    }
    
    public static synchronized void enter(String name, Function function, FileInfo file) {
        calls.push(new CallInfo(name, function, file));
    }
    
    public static synchronized void exit() {
        calls.pop();
    }

    public static synchronized Deque<CallInfo> getCalls() {
        return calls;
    }

    public record CallInfo(String name, Function function, @Nullable FileInfo file) {
        @Override
        public String toString() {
                return String.format("%s: %s", name, function.toString().trim());
            }
    }
}
