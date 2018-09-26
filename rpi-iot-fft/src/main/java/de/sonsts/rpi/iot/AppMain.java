package de.sonsts.rpi.iot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.SpectrumValue;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessageFactory;
import de.sonsts.rpi.iot.communication.common.messaage.MessageDispatcher;
import de.sonsts.rpi.iot.communication.common.messaage.MessageHandler;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.PayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.consumer.MessageConsumer;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;
import de.sonsts.rpi.iot.communication.producer.SendCallback;
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

        MessageProducer<DocumentMessage<SampleValuePayload<SpectrumValue>>> producer = new MessageProducer<DocumentMessage<SampleValuePayload<SpectrumValue>>>(
                IotTopic.LIVE.combine("fft"), new IotClientFactory(), "live.fft");
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
                    List<DoubleSampleValue> values = payload.getValues();
                    MappingPayloadDescriptor<Integer, String> payloadDescriptor = payload.getPayloadDescriptor();
                    String signalId = payloadDescriptor.getSignalMapping().get(values.get(0).getSignalId());
                    
                    // TODO: payload descriptor new descriptor fft(signals, freq)

                    if ((null != values) && (values.size() > 0))
                    {
                        List<SpectrumValue> spectrumValues = fft.compute(values);

                        System.out.println();

                        for (SpectrumValue spectrumValue : spectrumValues)
                        {
                            System.out.println(String.format("%s: %.1f;%.3f",signalId, spectrumValue.getFrequency(), spectrumValue.getMag()));
                        }

                        if ((null != producer) && (null != spectrumValues))
                        {
                            DocumentMessage<SampleValuePayload<SpectrumValue>> documentMessage = DocumentMessageFactory
                                    .createSpectrumValueMessage(new MappingPayloadDescriptor<Integer, String>(), spectrumValues);

                            producer.send(documentMessage, new SendCallback()
                            {
                                @Override
                                public <M extends AbstractMessage> void onSent(M message)
                                {
                                    // System.out.println(System.currentTimeMillis() + " Send Ok");
                                }

                                @Override
                                public <M extends AbstractMessage> void onError(M message, Exception exception)
                                {
                                    // System.out.println(System.currentTimeMillis() + " Sending failed "
                                    // + Arrays.toString(exception.getStackTrace()));
                                }
                            });
                        }
                    }
                }
            }
        });

        MessageConsumer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> consumer = new MessageConsumer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>>(
                IotTopic.LIVE, new IotClientFactory(), messageDispatcher, "live.samples");

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
