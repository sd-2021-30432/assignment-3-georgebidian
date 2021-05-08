package com.example.mma3.EventHandler;

import org.springframework.http.ResponseEntity;

public interface IEventHandler<T> {
    public ResponseEntity handle(T event);
}
