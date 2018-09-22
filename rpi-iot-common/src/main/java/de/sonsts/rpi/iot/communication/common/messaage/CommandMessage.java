package de.sonsts.rpi.iot.communication.common.messaage;

import de.sonsts.rpi.iot.communication.common.messaage.payload.MessagePayload;


public class CommandMessage<P extends MessagePayload> extends AbstractMessage<P>
{

    @Override
    public String toString()
    {
        return "CommandMessage [getCorrellationId()=" + getCorrellationId() + ", getFormatIndicator()=" + getFormatIndicator()
                + ", getPayload()=" + getPayload() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}
