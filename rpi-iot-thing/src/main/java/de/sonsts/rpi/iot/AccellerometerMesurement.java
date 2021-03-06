package de.sonsts.rpi.iot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.sonsts.rpi.i2c.sensor.Axis;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.NanoTime;
import de.sonsts.rpi.iot.communication.common.Quality;

public class AccellerometerMesurement
{
    private HashMap<Axis, List<DoubleSampleValue>> mSamples = new HashMap<Axis, List<DoubleSampleValue>>();

    public void add(Axis axis, NanoTime timeStamp, double value, Quality quality)
    {
        List<DoubleSampleValue> samples = mSamples.get(axis);
        if (null == samples)
        {
            samples = new ArrayList<DoubleSampleValue>();
            mSamples.put(axis, samples);
        }

        samples.add(new DoubleSampleValue(axis.getValue(), timeStamp, value, quality));
    }

    public List<DoubleSampleValue> getSamples(Axis axis)
    {
        List<DoubleSampleValue> retVal = mSamples.get(axis);
        return retVal;
    }

    public int size(Axis axis)
    {
        int retVal = -1;
        
        List<DoubleSampleValue> samples = mSamples.get(axis);
        if (null != samples)
        {
            retVal = samples.size();
        }
        
        return retVal;
    }
}
