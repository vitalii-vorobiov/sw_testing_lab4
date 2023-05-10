package com.sw_testing.lab4.exceptions;

public class CopterOutOfRangeException extends Exception {
    public CopterOutOfRangeException(String errorMessage) {
        super(errorMessage);
    }
}