package com.sw_testing.lab4.remote_control;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class RemoteControlsController {
    private ArrayList<RemoteControl> remoteControls;

    public RemoteControlsController() {
        remoteControls = new ArrayList<RemoteControl>();
    }

    public String CreateRemoteControl(int x, int y, int z) {
        var remoteControl = new RemoteControl(x, y, z);
        remoteControls.add(remoteControl);
        return remoteControl.GetID();
    }

    public RemoteControl GetRemoteControl(String id) throws NoSuchElementException {
        for (var remoteControl: remoteControls) {
            if (Objects.equals(remoteControl.GetID(), id)) {
                return remoteControl;
            }
        }
        throw new NoSuchElementException(String.format("There is no RemoteControl with ID: %s", id));
    }

    public boolean DeleteRemoteControl(String id) {
        for (var remoteControl : remoteControls) {
            if (Objects.equals(remoteControl.GetID(), id)) {
                if (remoteControl.IsConnected(null)) {
                    remoteControl.GetCopter().Disconnect();
                }
                return remoteControls.remove(remoteControl);
            }
        }
        return false;
    }
}
