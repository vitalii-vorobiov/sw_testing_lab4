package com.sw_testing.lab4.exceptions;

public class RemoteControlNotConnectedException extends Exception{
    public RemoteControlNotConnectedException(String errorMessage) {
        super(errorMessage);
    }
}