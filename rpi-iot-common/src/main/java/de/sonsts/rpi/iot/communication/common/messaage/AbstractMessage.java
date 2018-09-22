package de.sonsts.rpi.iot.communication.common.messaage;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.sonsts.rpi.iot.communication.common.messaage.payload.MessagePayload;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class AbstractMessage<P extends MessagePayload>
{
    private long correllationId = 0;
    private long formatIndicator = 0;
    private long timeStamp = System.currentTimeMillis();

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    private P payload;

    public long getCorrellationId()
    {
        return correllationId;
    }

    public void setCorrellationId(long correllationId)
    {
        this.correllationId = correllationId;
    }

    public long getFormatIndicator()
    {
        return formatIndicator;
    }

    public void setFormatIndicator(long formatIndicator)
    {
        this.formatIndicator = formatIndicator;
    }

    public P getPayload()
    {
        return payload;
    }

    public void setPayload(P payload)
    {
        this.payload = payload;
    }

}
