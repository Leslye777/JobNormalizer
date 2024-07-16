package com.example.jobnormalizer.exception;

public class InvalidJobTitleException extends RuntimeException {
    public InvalidJobTitleException(String message) {
        super(message);
    }
}
