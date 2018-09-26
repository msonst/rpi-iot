package de.sonsts.rpi.i2c.sensor;

public enum Axis
{
    X(0), Y(1), Z(2);
    private int mAxis;

    private Axis(int range)
    {
        mAxis = range;
    }

    public int getValue()
    {
        return mAxis;
    }
}