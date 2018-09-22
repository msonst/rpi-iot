package de.sonsts.rpi.iot;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import de.sonsts.rpi.i2c.sensor.ADXL345;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;

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

        MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> producer = new MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>>();
        HashMap<Integer, String> mapping = new HashMap<Integer, String>();
        // mapping.put(1, )

        I2CBus bus = null;
        I2CDevice dev = null;

        bus = I2CFactory.getInstance(I2CBus.BUS_1);
        if (null == bus)
        {
            throw new Exception("Failed to get I2C");
        }

        dev = bus.getDevice( ADXL345.ADXL345_DEVID);

        if (null == dev)
        {
            throw new Exception("Failed to get device");
        }

        ADXL345 adxl345 = new ADXL345(dev, ADXL345.DataRate.DR100, ADXL345.Range.R2G);
        mExecutorService.scheduleAtFixedRate(adxl345, 0, 10, TimeUnit.MILLISECONDS);
        mExecutorService.scheduleAtFixedRate(new AccellerometerProducer(adxl345, producer, mapping), 0, 10, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                mExecutorService.shutdown();
                try
                {
                    mExecutorService.awaitTermination(10, TimeUnit.SECONDS);
                }
                catch (InterruptedException e)
                {
                }
                mExecutorService.shutdown();
            }
        });

    }
}
