package com.sw_testing.lab4.exceptions;

public class RemoteControlAlreadyConnectedException extends Exception {
    public RemoteControlAlreadyConnectedException(String errorMessage) {
        super(errorMessage);
    }
}