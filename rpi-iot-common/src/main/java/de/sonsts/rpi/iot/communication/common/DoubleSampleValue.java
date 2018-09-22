package de.sonsts.rpi.iot.communication.common;

public class DoubleSampleValue extends SampleValue
{
    private double x;
    private double y;
    private double z;
    private int mSignalId;

    public DoubleSampleValue()
    {
    }

    public DoubleSampleValue(long timeStamp, int signalId, double x, double y, double z)
    {
        mSignalId = signalId;
        setTimeStamp(timeStamp);
        setX(x);
        setY(y);
        setZ(z);
    }

    public int getSignalId()
    {
        return mSignalId;
    }

    public void setSignalId(int signalId)
    {
        mSignalId = signalId;
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

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    @Override
    public String toString()
    {
        return "DoubleSampleValue [timeStamp=" + getTimeStamp() + ", x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
