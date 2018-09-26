package de.sonsts.rpi.iot.communication.common;

public enum Quality
{
    GOOD(1), UNINITIALIZED(2);

    private int value;

    private Quality(int value)
    {
       this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
