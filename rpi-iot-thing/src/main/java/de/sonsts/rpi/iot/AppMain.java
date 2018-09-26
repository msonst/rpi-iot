package de.sonsts.rpi.iot;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import de.sonsts.rpi.i2c.sensor.ADXL345;
import de.sonsts.rpi.i2c.sensor.ADXL345.DataRate;
import de.sonsts.rpi.i2c.sensor.Axis;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.IotClientFactory;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;
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

        DataRate dataRate = DataRate.DR200;
        
        MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>> producer = new MessageProducer<DocumentMessage<SampleValuePayload<DoubleSampleValue>>>(
                IotTopic.LIVE, new IotClientFactory(), "live.samples");
        HashMap<Integer, String> mapping = new HashMap<Integer, String>();
        mapping.put(Axis.X.getValue(), "X");
        mapping.put(Axis.Y.getValue(), "Y");
        mapping.put(Axis.Z.getValue(), "Z");

        I2CBus bus = null;
        I2CDevice dev = null;
        
        bus = I2CFactory.getInstance(I2CBus.BUS_1);
        if (null == bus)
        {
            throw new Exception("Failed to get I2C");
        }

        dev = bus.getDevice(ADXL345.ADXL345_DEVID);
        
        if (null == dev)
        {
            throw new Exception("Failed to get device");
        }

        ADXL345 adxl345 = new ADXL345(dev, dataRate, ADXL345.Range.R2G, ADXL345.CommunicationInterface.I2C);
        mExecutorService.scheduleAtFixedRate(adxl345, 0, ADXL345.SAMPLECOUNT * dataRate.getMs(), TimeUnit.MILLISECONDS);
        mExecutorService.scheduleAtFixedRate(new AccellerometerProducer(adxl345, producer, mapping), 0, 10, TimeUnit.MILLISECONDS);
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
                }
            }
        });

    }
}
