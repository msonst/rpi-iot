package de.sonsts.rpi.iot.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;

public class FftTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testFft()
    {
        List<DoubleSampleValue> values = new ArrayList<DoubleSampleValue>();

        HashMap<Integer, String> mapping = new HashMap<Integer, String>();
        for (int i = 0; i < 2048; i++)
        {
            mapping.put(i, "Signal" + i);
            values.add(new DoubleSampleValue(i, i, i, i, i));
        }

        // FFT of original data
        new Fft(null, null).compute(values.toArray(new DoubleSampleValue[0]));
    }
}
