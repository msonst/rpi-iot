package de.sonsts.rpi.iot.communication.common.messaage;

import java.util.HashMap;

public class MessageDispatcher
{
    private HashMap<Class<? extends AbstractMessage>, MessageHandler> mHanlers = new HashMap<Class<? extends AbstractMessage>, MessageHandler>();

    public <M extends AbstractMessage> void register(Class<M> message, MessageHandler handler)
    {
        mHanlers.put(message, handler);
    }

    public <M extends AbstractMessage> void handle(M message)
    {
        MessageHandler messageHandler = mHanlers.get(message.getClass());

        if (null != messageHandler)
        {
            messageHandler.handle(message);
        }
    }
}
