package com.sw_testing.lab4.remote_control;

import com.sw_testing.lab4.copter.Copter;
import com.sw_testing.lab4.exceptions.*;
import com.sw_testing.lab4.utils.Position;

import java.util.Objects;
import java.util.UUID;

public class RemoteControl {
    private final String ID;
    private final Position position;
    private Copter copter;

    public RemoteControl(int x, int y, int z) {
        ID = UUID.randomUUID().toString();
        position = new Position(x, y, z);
    }

    public String GetID() {
        return ID;
    }

    public boolean IsConnected(String id) {
        return copter != null && (id == null || Objects.equals(copter.GetID(), id));
    }

    public void ConnectCopter(Copter copter) throws CopterAlreadyConnectedException, RemoteControlAlreadyConnectedException {
        if (this.copter == null) {
            if (copter.IsConnected(null)) {
                if (copter.IsConnected(ID)) {
                    this.copter = copter;
                } else {
                    throw new CopterAlreadyConnectedException(String.format("The Copter: %s is already connected to another RemoteControll", copter.GetID()));
                }
            } else {
                this.copter = copter;
                copter.ConnectRemoteControll(this);
            }
        } else {
            throw new RemoteControlAlreadyConnectedException(String.format("This RemoteControll: %s is already connected to Copter: %s", ID, copter.GetID()));
        }
    }

    public void Disconnect() {
        copter = null;
    }

    public Copter GetCopter() {
        return copter;
    }

    public void SendCommand(RemoteControlCommands command) throws CopterOutOfRangeException, RemoteControlNotConnectedException, CopterNotConnectedException {
        if (copter != null) {
            switch (command) {
                case MOVE_FORWARD -> copter.MoveForward();
                case MOVE_BACKWARD -> copter.MoveBackward();
                case MOVE_RIGHT -> copter.MoveRight();
                case MOVE_LEFT -> copter.MoveLeft();
                case MOVE_UP -> copter.MoveUp();
                case MOVE_DOWN -> copter.MoveDown();
            }
        } else {
            throw new RemoteControlNotConnectedException("This RemoteControll is not connected to any Copter");
        }
    }

    public Position GetPosition() {
        return position;
    }
}