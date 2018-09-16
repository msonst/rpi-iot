package de.sonsts.rpi.i2c.sensor.utils;

public class Calibration
{
    private double mMean;
    private double mMax;
    private double mMin;

    public Calibration(double mean, double max, double min)
    {
        mMean = mean;
        mMax = max;
        mMin = min;
    }

    public double getMean()
    {
        return mMean;
    }

    public double getMax()
    {
        return mMax;
    }

    public double getMin()
    {
        return mMin;
    }

    public double calibrate(double value)
    {
        double retVal = 0;

        if (value >= getMin() && value <= getMax())
        {
            retVal = 0d;
        }
        else
        {
            retVal = value - getMean();
        }

        return retVal;
    }
}