package com.sw_testing.lab4.copter;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CoptersController {
    private ArrayList<Copter> copters;

    public CoptersController() {
        copters = new ArrayList<>();
    }

    public String CreateCopter(int x, int y, int z) {
        var copter = new Copter(x, y, z);
        copters.add(copter);
        return copter.GetID();
    }

    public Copter GetCopter(String id) throws NoSuchElementException {
        for (var copter: copters) {
            if (Objects.equals(copter.GetID(), id)) {
                return copter;
            }
        }
        throw new NoSuchElementException(String.format("There is no Copter with ID: %s", id));
    }

    public boolean DeleteCopter(String id) {
        for (var copter : copters) {
            if (Objects.equals(copter.GetID(), id)) {
                if (copter.IsConnected(null)) {
                    copter.GetRemoteControl().Disconnect();
                }
                return copters.remove(copter);
            }
        }
        return false;
    }
}
