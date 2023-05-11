package com.sw_testing.lab4.copter;

import com.sw_testing.lab4.Server;
import com.sw_testing.lab4.exceptions.*;
import com.sw_testing.lab4.remote_control.RemoteControl;
import com.sw_testing.lab4.utils.Position;

import java.util.Objects;
import java.util.UUID;

public class Copter {
    private final int MAX_DISTANCE = 100;
    private final int MAX_HEIGHT = 100;
    private final String ID;
    private Position position;
    private RemoteControl remoteControl;

    public Copter(int x, int y, int z) {
        ID = UUID.randomUUID().toString();
        position = new Position(x, y, z);
        Server.logger.info("Creating new Copter");
    }

    public String GetID() {
        return ID;
    }

    public boolean IsConnected(String id) {
        return remoteControl != null && (id == null || Objects.equals(remoteControl.GetID(), id));
    }

    public void ConnectRemoteControll(RemoteControl remoteControl) throws CopterAlreadyConnectedException, RemoteControlAlreadyConnectedException {
        if (this.remoteControl == null) {
            if (remoteControl.IsConnected(null)) {
                if (remoteControl.IsConnected(ID)) {
                    this.remoteControl = remoteControl;
                } else {
                    throw new RemoteControlAlreadyConnectedException(String.format("The RemoteControll: %s is already connected to another Copter", remoteControl.GetID()));
                }
            } else {
                this.remoteControl = remoteControl;
                remoteControl.ConnectCopter(this);
            }
        } else {
            throw new CopterAlreadyConnectedException(String.format("This Copter: %s is already connected to RemoteControll: %s", ID, remoteControl.GetID()));
        }
    }

    public void Disconnect() {
        remoteControl = null;
    }

    public RemoteControl GetRemoteControl() {
        return remoteControl;
    }

    public void MoveForward() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX() + 15, position.GetY(), position.GetZ());
            if (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public void MoveBackward() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX() - 15, position.GetY(), position.GetZ());
            if (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public void MoveRight() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX(), position.GetY() + 15, position.GetZ());
            if (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public void MoveLeft() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX(), position.GetY() - 15, position.GetZ());
            if (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public void MoveUp() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX(), position.GetY(), position.GetZ() + 15);
            if ((newPosition.GetZ() > MAX_HEIGHT) || (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE)) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public void MoveDown() throws CopterOutOfRangeException, CopterNotConnectedException {
        if (remoteControl != null) {
            var newPosition = new Position(position.GetX(), position.GetY(), position.GetZ() - 15);
            if ((newPosition.GetZ() < 0) || (newPosition.DistanceTo(remoteControl.GetPosition()) > MAX_DISTANCE)) {
                throw new CopterOutOfRangeException("Your copter will be out of range after moving to new position");
            }
            position = newPosition;
        } else {
            throw new CopterNotConnectedException("This Copter is not connected to any RemoteControll");
        }
    }

    public Position GetPosition() {
        return position;
    }
}
