package com.example.spring_advanced.trace.callback;

public interface TraceCallback<T> {
    T call();
}
