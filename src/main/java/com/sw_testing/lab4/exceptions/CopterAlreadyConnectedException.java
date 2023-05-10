package com.sw_testing.lab4.exceptions;

public class CopterAlreadyConnectedException extends Exception {
    public CopterAlreadyConnectedException(String errorMessage) {
        super(errorMessage);
    }
}