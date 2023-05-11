package com.sw_testing.lab4.utils;

import java.util.ArrayList;

public class SimulationScenario {
    public Position initialRemoteControlPosition;
    public Position initialCopterPosition;
    public Path[] path;

    public SimulationScenario(Position initialRemoteControlPosition, Position initialCopterPosition, Path[] path) {
        this.initialRemoteControlPosition = initialRemoteControlPosition;
        this.initialCopterPosition = initialCopterPosition;
        this.path = path;
    }
}
