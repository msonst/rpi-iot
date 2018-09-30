package de.sonsts.rpi.iot.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.NanoTime;
import de.sonsts.rpi.iot.communication.common.Quality;

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
        for (int i = 0; i < 2049; i++)
        {
            mapping.put(i, "Signal" + i);
            values.add(new DoubleSampleValue(i, new NanoTime(i, 0), 3, Quality.GOOD));
        }

        // FFT of original data
        System.out.println(new Fft(null, null).compute(values));
    }
}
