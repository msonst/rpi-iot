package de.sonsts.rpi.iot.communication.common.messaage;

import de.sonsts.rpi.iot.communication.common.messaage.payload.CrudCommandPayload;
import de.sonsts.rpi.iot.communication.common.messaage.payload.CrudCommandPayload.Operation;

public class CommandMessageFactory
{
    public static CommandMessage<CrudCommandPayload> createCommandMessage(Operation operation)
    {
       CommandMessage<CrudCommandPayload>  retVal = new CommandMessage<CrudCommandPayload> ();
        
        retVal.setPayload(new CrudCommandPayload(operation));
        
        return retVal;
    }
}
