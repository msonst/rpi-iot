package de.sonsts.rpi.iot.communication.common.messaage;

import de.sonsts.rpi.iot.communication.common.messaage.payload.StatusEventPayload;

public class EventMessageFactory
{
    public static EventMessage<StatusEventPayload> createStatusEventMessage()
    {
        EventMessage<StatusEventPayload> retVal = new EventMessage<StatusEventPayload>();

        retVal.setPayload(new StatusEventPayload());

        return retVal;
    }
}
