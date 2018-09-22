package de.sonsts.rpi.iot.communication.common.messaage;

import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.payload.CrudCommandPayload;
import de.sonsts.rpi.iot.communication.common.messaage.payload.CrudCommandPayload.Operation;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;

public class CommandMessageFactory
{
    public static CommandMessage<SampleValuePayload<DoubleSampleValue>> createCommandMessage(DoubleSampleValue... values)
    {
        CommandMessage<SampleValuePayload<DoubleSampleValue>>  retVal = new CommandMessage<SampleValuePayload<DoubleSampleValue>> ();
        
        retVal.setPayload(new SampleValuePayload<DoubleSampleValue>(values));
        
        return retVal;
    }

    public static CommandMessage<CrudCommandPayload> createCommandMessage(Operation operation)
    {
       CommandMessage<CrudCommandPayload>  retVal = new CommandMessage<CrudCommandPayload> ();
        
        retVal.setPayload(new CrudCommandPayload(operation));
        
        return retVal;
    }
}
