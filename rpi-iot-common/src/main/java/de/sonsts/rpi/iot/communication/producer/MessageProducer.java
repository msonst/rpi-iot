package de.sonsts.rpi.iot.communication.producer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;

public class MessageProducer<M extends AbstractMessage> implements Runnable
{
    private static final int RECONNECT = 3;

    private IotTopic mTopic;
    private ObjectMapper mMapper;
    private Connection mConnection;
    private Channel mChannel;

    private class QueueEntry
    {
        private M mMessage;
        private SendCallback mCallback;

        public QueueEntry(M message, SendCallback callback)
        {
            mMessage = message;
            mCallback = callback;
        }

        public M getMessage()
        {
            return mMessage;
        }

        public SendCallback getCallback()
        {
            return mCallback;
        }
    }

    private ConcurrentLinkedQueue<QueueEntry> mQueue = new ConcurrentLinkedQueue<QueueEntry>();
    private String mRouting;
    private String mExchange;
    private int mRetry = RECONNECT;
    private IotClientFactory mIotClientFactory;

    public MessageProducer(IotTopic topic, IotClientFactory iotClientFactory, String exchange) throws IOException, TimeoutException
    {
        mTopic = topic;
        mIotClientFactory = iotClientFactory;
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
        M message = null;
        QueueEntry queueEntry = null;

        do
        {
            queueEntry = mQueue.poll();
            if (null != queueEntry)
            {
                message = queueEntry.getMessage();
                SendCallback callback = queueEntry.getCallback();

                if (null != message)
                {
                    StringWriter jsonData = new StringWriter();

                    try
                    {
                        mMapper.writeValue(jsonData, message);

                        mChannel.basicPublish(mExchange, "", MessageProperties.PERSISTENT_TEXT_PLAIN, jsonData.toString().getBytes());
                        if (null != callback) callback.onSent(message);
                        mRetry = RECONNECT;
                    }
                    catch (Exception e)
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
                        else if (null != callback) callback.onError(message, e);
                    }
                }
            }
        } while (null != queueEntry);
    }

    public void send(M message, SendCallback callback)
    {
        mQueue.add(new QueueEntry(message, callback));
    }

    public void sendSync(M message)
    {
    }
}
