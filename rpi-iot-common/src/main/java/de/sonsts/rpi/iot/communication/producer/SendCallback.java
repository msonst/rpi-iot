package de.sonsts.rpi.iot.communication.producer;

import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;

public interface SendCallback
{
    public <M extends AbstractMessage> void onSent(M message);

    public <M extends AbstractMessage> void onError(M message, Exception exception);
}
