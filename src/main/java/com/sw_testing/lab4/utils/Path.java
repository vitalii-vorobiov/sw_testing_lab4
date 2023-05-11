package com.sw_testing.lab4.utils;

import com.sw_testing.lab4.remote_control.RemoteControlCommands;

public class Path {
    public String command;
    public Position position;

    public Path(String command, Position position) {
        this.command = command;
        this.position = position;
    }
}
