package de.sonsts.rpi.i2c.sensor.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;

public class SampleBuffer
{
    private static final long serialVersionUID = 7627084267329172654L;

    private Queue<DoubleSampleValue> mSamples = new LinkedList<DoubleSampleValue>();
    private Calibration mCalibrationX;
    private Calibration mCalibrationY;
    private Calibration mCalibrationZ;
    private double mGain;

    public SampleBuffer()
    {
    }

    public void setCalibration(Calibration calibrationX, Calibration calibrationY, Calibration calibrationZ, double gain)
    {
        mCalibrationX = calibrationX;
        mCalibrationY = calibrationY;
        mCalibrationZ = calibrationZ;
        mGain = gain;
    }

    public void addSample(DoubleSampleValue sample)
    {
        mSamples.add(sample);
    }

    public void addRawSample(long timeStamp, int signalId, double x, double y, double z)
    {
//        x = mCalibrationX.calibrate(x);
//        y = mCalibrationY.calibrate(y);
//        z = mCalibrationZ.calibrate(z);

        synchronized (mSamples)
        {
            mSamples.add(new DoubleSampleValue(timeStamp, signalId, x, y, z));
        }
    }

    public DoubleSampleValue[] getSamples(int count)
    {
        DoubleSampleValue[] retVal = null;
        List<DoubleSampleValue> tmp = new ArrayList<>();

        synchronized (mSamples)
        {
            for (int i = 0; ((i < count) && (!mSamples.isEmpty())); i++)
            {
                tmp.add(mSamples.poll());
            }
        }

        retVal = tmp.toArray(new DoubleSampleValue[0]);

        return (null != retVal) ? retVal : new DoubleSampleValue[0];
    }

    public DoubleSampleValue[] getAllSamples()
    {
        DoubleSampleValue[] retVal = null;

        synchronized (mSamples)
        {
            retVal = mSamples.toArray(new DoubleSampleValue[0]);
            mSamples.clear();
        }

        return (null != retVal) ? retVal : new DoubleSampleValue[0];
    }

    public static int toDouble(int high, int low)
    {
        int retVal = 0;

        retVal = (high << 8) | (low);

        return retVal;
    }

    public void addRawSample(long timeStamp, byte[] buffer)
    {
        double[] values = new double[3];

        for (int i = 0; i < 3; i++)
        {
            values[i] = toDouble(buffer[i * 2 + 1], buffer[i * 2]) * mGain;
        }

        addRawSample(timeStamp, 0, values[0], values[1], values[2]);
    }

    public int size()
    {
        return mSamples.size();
    }
}
