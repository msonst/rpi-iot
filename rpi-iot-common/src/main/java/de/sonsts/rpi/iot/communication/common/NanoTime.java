package de.sonsts.rpi.iot.communication.common;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NanoTime
{
    private static final long NANOSECONDS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1);

    private long milliSeconds = 0;
    private long nanoSeconds = 0;

    public NanoTime()
    {
    }

    public NanoTime(long milliSeconds, long nanoSeconds)
    {
        setMilliSeconds(milliSeconds);
        setNanoSeconds(nanoSeconds);
    }

    public long getMilliSeconds()
    {
        return milliSeconds;
    }

    public void setMilliSeconds(long milliSeconds)
    {
        this.milliSeconds = milliSeconds;
    }

    public long getNanoSeconds()
    {
        return nanoSeconds;
    }

    public void setNanoSeconds(long nanoSeconds)
    {
        long ms = nanoSeconds / NANOSECONDS_PER_MILLISECOND;
        this.milliSeconds += ms;

        if (nanoSeconds >= 0)
        {
            this.nanoSeconds = nanoSeconds - ms * NANOSECONDS_PER_MILLISECOND;
        }
        else
        {
            this.nanoSeconds = nanoSeconds - ms * NANOSECONDS_PER_MILLISECOND;
        }
    }

    public NanoTime subtract(NanoTime other)
    {
        NanoTime retVal = new NanoTime(this.milliSeconds - other.getMilliSeconds(), this.nanoSeconds - other.getNanoSeconds());

        return retVal;
    }

    public void subtractNanos(long nanoSeconds)
    {
        long ms = nanoSeconds / NANOSECONDS_PER_MILLISECOND;
        this.milliSeconds -= ms;

        this.nanoSeconds -= nanoSeconds - ms * NANOSECONDS_PER_MILLISECOND;
        if (this.nanoSeconds < 0)
        {
            this.milliSeconds--;
            this.nanoSeconds += NANOSECONDS_PER_MILLISECOND;
        }
    }

    public long longNano()
    {
        return milliSeconds * NANOSECONDS_PER_MILLISECOND + nanoSeconds; // Watch out for overflow
    }

    @Override
    public String toString()
    {
        return "NanoTime [milliSeconds=" + milliSeconds + ", nanoSeconds=" + nanoSeconds + "]";
    }

}
