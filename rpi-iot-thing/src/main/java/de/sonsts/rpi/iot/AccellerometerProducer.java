package de.sonsts.rpi.iot;

import java.util.Arrays;
import java.util.HashMap;

import de.sonsts.rpi.i2c.sensor.ADXL345;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessageFactory;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;
import de.sonsts.rpi.iot.communication.producer.SendCallback;

public class AccellerometerProducer implements Runnable
{
    private ADXL345 mAdxl345 = null;
    private MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> mProducer;
    private HashMap<Integer, String> mMapping;

    public AccellerometerProducer(ADXL345 adxl345, MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> producer, HashMap<Integer, String> mapping) 
    {
        super();
        mAdxl345 = adxl345;
        mProducer = producer;
        mMapping = mapping;
    }

    @Override
    public void run()
    {
        if ((null == mAdxl345) || (null == mProducer)) return;

        DoubleSampleValue[] values = null;
        try
        {
            values = mAdxl345.getSamples(32);
        }
        catch (Exception e)
        {
        }

        if ((null != values) && (0 != values.length))
        {
            DocumentMessage<SampleValuePayload<DoubleSampleValue>> documentMessage = DocumentMessageFactory.createDoubleSampleValueMessage(
                    new MappingPayloadDescriptor<Integer, String>(mMapping), values);
            
            mProducer.send(documentMessage, new SendCallback()
            {
                @Override
                public <M extends AbstractMessage> void onSent(M message)
                {
                    System.out.println("Send Ok");
                }
                
                @Override
                public <M extends AbstractMessage> void onError(M message, Exception exception)
                {
                    System.out.println("Sending failed " + Arrays.toString(exception.getStackTrace()));
                }
            });
            
            for (DoubleSampleValue s : values)
            {
                System.out.println(s);
            }
        }
    }

}
