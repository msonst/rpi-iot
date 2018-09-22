package de.sonsts.rpi.iot.communication.common.messaage;

public interface MessageHandler<M extends AbstractMessage>
{
    public void handle(M message);
}
