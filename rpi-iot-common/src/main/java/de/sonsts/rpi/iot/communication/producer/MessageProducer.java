package de.sonsts.rpi.iot.communication.producer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;

public class MessageProducer<M extends AbstractMessage> implements Runnable
{
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

    public MessageProducer()
    {
    }

    public MessageProducer(IotTopic topic, IotClientFactory iotClientFactory) throws IOException, TimeoutException
    {
        mTopic = topic;
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
        M message = null;
        do
        {
            QueueEntry queueEntry = mQueue.poll();
            message = queueEntry.getMessage();
            SendCallback callback = queueEntry.getCallback();
            
            if (null != message)
            {
                String jsonMessage = mMapper.toJson(message);

                try
                {
                    mChannel.basicPublish("", mTopic.getTopic(), MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMessage.getBytes("UTF-8"));
                    callback.onSent(message);
                }
                catch (Exception e)
                {
                    callback.onError(message, e);
                }
            }
        } while (null != message);
    }

    public void send(M message, SendCallback callback)
    {
        mQueue.add(new QueueEntry(message, callback));
    }

    public void sendSync(M message)
    {
    }
}
