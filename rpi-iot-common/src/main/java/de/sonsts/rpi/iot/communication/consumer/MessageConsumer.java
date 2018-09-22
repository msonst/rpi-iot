package de.sonsts.rpi.iot.communication.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.MessageDispatcher;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;

public class MessageConsumer<M extends AbstractMessage> implements Runnable
{
    private ObjectMapper mMapper;
    private IotTopic mTopic;
    private Connection mConnection;
    private Channel mChannel;
    private MessageDispatcher mDispacher;

    public MessageConsumer(IotTopic topic, IotClientFactory iotClientFactory, MessageDispatcher dispacher) throws IOException,
            TimeoutException
    {
        mTopic = topic;
        mDispacher = dispacher;

        mMapper = JsonFactory.create();

        mConnection = iotClientFactory.getConnection();
        mChannel = mConnection.createChannel();
    }

    public void open()
    {

    }

    public void close()
    {
    }

    @Override
    public void run()
    {
        try
        {
            String ctag = mChannel.basicConsume(mTopic.getTopic(), true, new DefaultConsumer(mChannel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException
                {
                    Object object = mMapper.fromJson(body);
                    if (object instanceof AbstractMessage)
                    {
                        AbstractMessage msg = (AbstractMessage) object;
                        mDispacher.handle(msg);
                    }
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
