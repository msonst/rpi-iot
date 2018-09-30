package de.sonsts.rpi.iot;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.sonsts.rpi.iot.communication.common.NanoTime;

public class NanoTimeTest
{
    private static final long NANOSECONDS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1);

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test()
    {
        NanoTime uut = new NanoTime(1, 0);
        uut.subtractNanos(1);
        Assert.assertTrue(uut.getMilliSeconds() == 0);
        Assert.assertTrue(uut.getNanoSeconds() != 0);
        Assert.assertTrue(uut.getNanoSeconds() > 0);
        Assert.assertTrue(uut.getNanoSeconds() == (NANOSECONDS_PER_MILLISECOND - 1));
    }

}
