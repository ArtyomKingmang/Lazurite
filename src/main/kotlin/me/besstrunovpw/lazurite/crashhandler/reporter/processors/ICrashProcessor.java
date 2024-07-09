package me.besstrunovpw.lazurite.crashhandler.reporter.processors;

public interface ICrashProcessor {
    
    String getName();
    String proceed(Throwable throwable);
    
}
