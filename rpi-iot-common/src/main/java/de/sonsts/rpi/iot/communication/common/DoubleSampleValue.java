package de.sonsts.rpi.iot.communication.common;


public class DoubleSampleValue extends SampleValue
{
    private double value;

    public DoubleSampleValue()
    {
    }

    public DoubleSampleValue(int signalId, NanoTime timeStamp, double value, Quality quality)
    {
        setSignalId(signalId);
        setTimeStamp(timeStamp);
        setValue(value);
        setQuality(quality);
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "DoubleSampleValue [signalId=" + getSignalId() + ", timeStamp=" + getTimeStamp() + ", value=" + getValue() + ", quality="
                + getQuality() + "]";
    }
}
