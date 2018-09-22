package de.sonsts.rpi.iot;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.MessageDispatcher;
import de.sonsts.rpi.iot.communication.common.messaage.MessageHandler;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.consumer.MessageConsumer;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;
import de.sonsts.rpi.iot.module.Fft;

/**
 * @author sonst00m
 *
 */
public class AppMain
{
    private static ScheduledExecutorService mExecutorService;

    public static void main(String[] args) throws Exception
    {
        mExecutorService = Executors.newScheduledThreadPool(2);

        MessageProducer<DocumentMessage<SampleValuePayload<ComplexValue>>> producer = new MessageProducer<DocumentMessage<SampleValuePayload<ComplexValue>>>(
                IotTopic.LIVE.combine("fft"));
        HashMap<Integer, String> mapping = new HashMap<Integer, String>();
        // mapping.put(1, )

        Fft fft = new Fft(producer, mapping);

        MessageDispatcher messageDispatcher = new MessageDispatcher();
        messageDispatcher.register(DocumentMessage.class, new MessageHandler<DocumentMessage<SampleValuePayload<DoubleSampleValue>>>()
        {
            @Override
            public void handle(DocumentMessage<SampleValuePayload<DoubleSampleValue>> message)
            {
                SampleValuePayload<DoubleSampleValue> payload = message.getPayload();
                if (null != payload)
                {
                    DoubleSampleValue[] values = payload.getValues();
                    if (null != values)
                    {
                        fft.compute(values);
                    }
                }
            }
        });

        MessageConsumer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> consumer = new MessageConsumer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>>(
                IotTopic.LIVE, new IotClientFactory(), messageDispatcher);

        producer.open();
        consumer.open();

        mExecutorService.scheduleAtFixedRate(consumer, 0, 10, TimeUnit.MILLISECONDS);
        mExecutorService.scheduleAtFixedRate(producer, 0, 10, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    System.out.println("attempt to shutdown executor");
                    mExecutorService.shutdown();
                    mExecutorService.awaitTermination(5, TimeUnit.SECONDS);
                }
                catch (InterruptedException e)
                {
                    System.err.println("tasks interrupted");
                }
                finally
                {
                    if (!mExecutorService.isTerminated())
                    {
                        System.err.println("cancel non-finished tasks");
                    }
                    mExecutorService.shutdownNow();
                    System.out.println("shutdown finished");

                    producer.close();
                    consumer.close();
                }
            }
        });
    }
}
