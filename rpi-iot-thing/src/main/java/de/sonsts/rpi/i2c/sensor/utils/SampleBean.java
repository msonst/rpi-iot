package de.sonsts.rpi.i2c.sensor.utils;

public class SampleBean
{
    private long timeStamp;
    private double x;
    private double y;
    private double z;

    public SampleBean(long timeStamp, double x, double y, double z)
    {
       this.timeStamp = timeStamp;
       this.x = x;
       this.y = y;
       this.z = z;
    }

    public long getTimeStamp()
    {
        return this.timeStamp;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }
}
