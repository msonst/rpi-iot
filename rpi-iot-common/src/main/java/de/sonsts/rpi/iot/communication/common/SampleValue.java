package de.sonsts.rpi.iot.communication.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
public abstract class SampleValue
{
    private NanoTime timeStamp;
    private Quality quality = Quality.UNINITIALIZED;
    private int signalId;

    public int getSignalId()
    {
        return signalId;
    }

    public void setSignalId(int signalId)
    {
        this.signalId = signalId;
    }

    public Quality getQuality()
    {
        return quality;
    }

    public void setQuality(Quality quality)
    {
        this.quality = quality;
    }

    public NanoTime getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(NanoTime timeStamp)
    {
        this.timeStamp = timeStamp;
    }
}
