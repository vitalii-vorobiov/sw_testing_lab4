package com.sw_testing.lab4.utils;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int GetX() {
        return x;
    }
    public int GetY() {
        return y;
    }
    public int GetZ() {
        return z;
    }
    public double DistanceTo(Position position) {
        return Math.sqrt(Math.pow(x - position.GetX(), 2) + Math.pow(y - position.GetY(), 2) + Math.pow(z - position.GetZ(), 2));
    }
    @Override
    public String toString() {
        return String.format("X: %d, Y: %d, Z: %d", x, y, z);
    }
}