package com.sw_testing.lab4.exceptions;

public class CopterNotConnectedException extends Exception {
    public CopterNotConnectedException(String errorMessage) {
        super(errorMessage);
    }
}