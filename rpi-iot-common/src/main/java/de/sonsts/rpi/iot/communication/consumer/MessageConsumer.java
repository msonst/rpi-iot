package de.sonsts.rpi.iot.communication.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AlreadyClosedException;
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
    private static final int RECONNECT = 3;
    private ObjectMapper mMapper;
    private IotTopic mTopic;
    private Connection mConnection;
    private Channel mChannel;
    private MessageDispatcher mDispacher;
    private String mQueueName;
    private int mRetry = RECONNECT;
    private IotClientFactory mIotClientFactory;
    private String mExchange;

    public MessageConsumer(IotTopic topic, IotClientFactory iotClientFactory, MessageDispatcher dispacher, String exchange)
            throws IOException, TimeoutException
    {
        mTopic = topic;
        mIotClientFactory = iotClientFactory;
        mDispacher = dispacher;
        mExchange = exchange;

        mMapper = new ObjectMapper();
        mMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mMapper.setSerializationInclusion(Include.NON_NULL);

        connect();
    }

    private void connect() throws IOException, TimeoutException
    {
        mConnection = mIotClientFactory.getConnection();
        mChannel = mConnection.createChannel();

        mChannel.exchangeDeclare(mExchange, "fanout");
        mQueueName = mChannel.queueDeclare().getQueue();
        mChannel.queueBind(mQueueName, mExchange, "");
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
            String ctag = mChannel.basicConsume(mQueueName, true, new DefaultConsumer(mChannel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException
                {
                    try
                    {
                        AbstractMessage message = mMapper.readValue(body, AbstractMessage.class);
                        mDispacher.handle(message);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            mRetry = RECONNECT;
        }
        catch (AlreadyClosedException | IOException e)
        {
            if ((e instanceof AlreadyClosedException) && (mRetry-- > 0))
            {
                try
                {
                    System.out.println("Reconnect attempt " + (RECONNECT - mRetry + 1));
                    connect();
                }
                catch (Exception e1)
                {
                    e1 = null;
                }
            }
        }
    }
}
